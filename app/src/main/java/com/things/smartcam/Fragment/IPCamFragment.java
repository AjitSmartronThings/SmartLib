package com.things.smartcam.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.things.smartcam.R;
import com.things.smartcam.activities.PlaylistActivity;


public class IPCamFragment extends Fragment {

    private static IPCamFragment INSTANCE = null;

    public IPCamFragment() {
        // Required empty public constructor
    }

    public static IPCamFragment getInstace() {
        if (INSTANCE == null) INSTANCE = new IPCamFragment();
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ipcam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView vdp = (CardView) view.findViewById(R.id.vdp);
        CardView ipcamera = (CardView) view.findViewById(R.id.ipcamera);

        ipcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(),PlaylistActivity.class);
                startActivity(i);
            }
        });

    }


}
