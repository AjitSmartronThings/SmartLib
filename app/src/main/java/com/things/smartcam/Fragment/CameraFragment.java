package com.things.smartcam.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.things.smartcam.Add_Camera_Manually;
import com.things.smartcam.DbCamera;
import com.things.smartcam.MainActivity;
import com.things.smartcam.R;
import com.things.smartcam.TronXCamera;
import com.things.smartcam.adapter.CameraAdapter;
import com.things.smartcam.adapter.ItemClickListener;
import com.things.smartlib.FindDevicesThread;
import com.things.smartlib.OnvifManager;
import com.things.smartlib.listeners.DeviceMediaProfileListener;
import com.things.smartlib.listeners.OnvifDeviceInformationListener;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.listeners.OnvifServiceListener;
import com.things.smartlib.listeners.OnvifStreamUriListener;
import com.things.smartlib.models.Device;
import com.things.smartlib.models.DeviceMediaProfile;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifDeviceInformation;
import com.things.smartlib.models.OnvifServices;
import com.things.smartlib.player.TronXPlayer;
import com.things.smartlib.responses.OnvifResponse;

import java.util.ArrayList;
import java.util.List;


public class CameraFragment extends Fragment implements ItemClickListener,OnvifResponseListener {

    private static CameraFragment INSTANCE = null;

    private static final String TAG = CameraFragment.class.getSimpleName();

    private DbCamera mDatabase;

    private RecyclerView mRecyclerView;
    private CameraAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView emptyData;
    private Button addDevice;

    private RelativeLayout emptyLayout;

    DeviceMediaProfile mediaProfile;
    OnvifManager onvifManager;
    OnvifDevice onvifDevice;
    String profile,streamuri;
    Spinner spinner;
    String devurl = null;
    ProgressDialog pg;


    public CameraFragment() {
        // Required empty public constructor
    }
    public static CameraFragment getInstace() {
        if (INSTANCE == null) INSTANCE = new CameraFragment();
        return INSTANCE;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_withdraw, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase = new DbCamera(getActivity());
        List<TronXCamera> allCameras = mDatabase.getallCameras();

        if(allCameras.size() > 0){
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            mAdapter = new CameraAdapter(getActivity(), allCameras,this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        }else {
            mRecyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "There is no device in the database. Start adding now", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        addDevice = (Button) view.findViewById(R.id.addDevice);
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)



        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullScreenDialog dialog = new FullScreenDialog();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, FullScreenDialog.TAG);
            }
        });
    }


    @Override
    public void onItemClick(View v, int pos,TronXCamera tronXCamera) {
        Toast.makeText(getActivity(), ""+tronXCamera.getInternalHost()+" "+tronXCamera.getInternalHttp(), Toast.LENGTH_SHORT).show();
        devurl = tronXCamera.getInternalHost()+":"+tronXCamera.getInternalHttp();
        addDevice();

    }

    private void addDevice(){

        if(devurl==null)
        {
            Toast.makeText(getActivity(), "Enter Device IP or Search for devices", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager = new OnvifManager();
        onvifManager.setOnvifResponseListener(this);

        onvifDevice = new OnvifDevice(devurl, "admin", "admin");
       getServices();
    }

    private void getServices() {

        if(onvifDevice == null)
        {
            Toast.makeText(getActivity(), "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager.getServices(onvifDevice, new OnvifServiceListener() {
            @Override
            public void onServicesReceived(OnvifDevice onvifDevice, OnvifServices onvifServices) {
                if (onvifServices != null) {
                          /*  Toast.makeText(getActivity(),"Service : "+onvifServices.getServicepath()+"\n"
                                    +"Device : "+onvifServices.getDeviceinfomationpath()+"\n"
                                    +"Profile : "+onvifServices.getProfilespath()+"\n"
                                    +"Stream : "+onvifServices.getStreamURIpath(), Toast.LENGTH_SHORT).show();*/
                            getDeviceInformation();
                }
                else
                {
                            Toast.makeText(getActivity(), "No Servcies Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDeviceInformation() {
        if(onvifDevice == null)
        {
            Toast.makeText(getActivity(), "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }
        onvifManager.getDeviceInformation(onvifDevice, new OnvifDeviceInformationListener() {
            @Override
            public void onDeviceInformationReceived(OnvifDevice onvifDevice, OnvifDeviceInformation onvifDeviceInformation) {

                if (onvifDeviceInformation != null) {

                           /* Toast.makeText(getActivity(), "Model :: "+onvifDeviceInformation.getModel()+"\n"
                                    +"Manufacturer :: "+onvifDeviceInformation.getManufacturer()+"\n"
                                    +"Serial Number :: "+onvifDeviceInformation.getSerialNumber()+"\n"
                                    +"Hardware ID :: "+onvifDeviceInformation.getHardwareId(), Toast.LENGTH_SHORT).show();*/
                            getProfiles();
                }
                else
                {
                            Toast.makeText(getActivity(), "Device Info Not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getProfiles() {

        if(onvifDevice == null)
        {
            Toast.makeText(getActivity(), "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager.getMediaProfiles(onvifDevice, new DeviceMediaProfileListener() {
            @Override
            public void onMediaProfileReceived(OnvifDevice onvifDevice, List<DeviceMediaProfile> mediaProfiles) {
                if (mediaProfiles != null) {
                    mediaProfile=mediaProfiles.get(0);
                            //Toast.makeText(getActivity(),""+mediaProfiles.get(0).getToken()+"\n"+mediaProfiles.get(1).getToken(), Toast.LENGTH_SHORT).show();
                            getStreamUri();
                }
                else
                {

                            Toast.makeText(getActivity(), "Profiles Not found", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getStreamUri() {

        if(onvifDevice == null)
        {
            Toast.makeText(getActivity(), "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager.getMediaStreamURI(onvifDevice, mediaProfile, new OnvifStreamUriListener() {
            @Override
            public void onvifStreamUriReceived(OnvifDevice onvifDevice, DeviceMediaProfile onvifMediaProfile, String s) {
                if (s != null) {
                    profile = onvifMediaProfile.getName();
                    streamuri = s;
                            //Toast.makeText(getActivity(),"Stream URI ::"+s, Toast.LENGTH_SHORT).show();
                            play();
                }
                else {
                    Toast.makeText(getActivity(), "URI Not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void play() {
        if(onvifDevice == null)
        {
            Toast.makeText(getActivity(), "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(), TronXPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("streamUri", streamuri);
        bundle.putString("profile", profile);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse onvifResponse) {

    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {

    }
}
