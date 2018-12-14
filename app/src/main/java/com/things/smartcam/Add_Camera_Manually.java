package com.things.smartcam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.things.smartcam.util.Commons;
import com.things.smartlib.OnvifManager;
import com.things.smartlib.listeners.DeviceMediaProfileListener;
import com.things.smartlib.listeners.OnvifDeviceInformationListener;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.listeners.OnvifServiceListener;
import com.things.smartlib.listeners.OnvifStreamUriListener;
import com.things.smartlib.models.DeviceMediaProfile;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifDeviceInformation;
import com.things.smartlib.models.OnvifServices;
import com.things.smartlib.player.TronXPlayer;
import com.things.smartlib.responses.OnvifResponse;

import java.io.Serializable;
import java.util.List;

public class Add_Camera_Manually extends AppCompatActivity implements OnvifResponseListener {

    private static final String TAG = Add_Camera_Manually.class.getSimpleName();

    String cameraName,cameraInternalUrl,cameraHttp,cameraUsername,cameraPassword;

    EditText txtcameraName,txtcameraInternalUrl,txtcameraHttp,txtcameraUsername,txtcameraPassword;

    Button addCamera;

    TronXCamera tronXCamera;

    OnvifManager onvifManager;

    OnvifDevice onvifDevice;

    DeviceMediaProfile mediaProfile;
    String profile,streamuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__camera__manually);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbar_title);
        toolbartext.setText("Add Camera");
        setSupportActionBar(toolbar);

        onvifManager = new OnvifManager();
        onvifManager.setOnvifResponseListener(this);

        txtcameraName = (EditText) findViewById (R.id.cameraName);
        txtcameraInternalUrl = (EditText) findViewById (R.id.cameraInternalUrl);
        txtcameraHttp = (EditText) findViewById (R.id.cameraHttp);
        txtcameraUsername = (EditText) findViewById (R.id.cameraUsername);
        txtcameraPassword = (EditText) findViewById (R.id.cameraPassword);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            String ipaddress = bundle.getString("ipaddress");
            String ipport = bundle.getString("ipport");
            txtcameraInternalUrl.setText(ipaddress);
            txtcameraHttp.setText(ipport);
        }

        addCamera = (Button) findViewById (R.id.addCamera);


        addCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraName = txtcameraName.getText().toString();
                cameraInternalUrl = txtcameraInternalUrl.getText().toString();
                cameraHttp = txtcameraHttp.getText().toString();
                cameraUsername = txtcameraUsername.getText().toString();
                cameraPassword = txtcameraPassword.getText().toString();

                if (!Commons.isEmptyEditText(cameraName)) {
                    Toast.makeText(Add_Camera_Manually.this, "Please enter Camera Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Commons.isIP(cameraInternalUrl)) {
                    Toast.makeText(Add_Camera_Manually.this, "Please enter valid ip", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Commons.isPortStringValid(cameraHttp)) {
                    Toast.makeText(Add_Camera_Manually.this, "Please enter valid port No. between 0 to 65535", Toast.LENGTH_SHORT).show();
                    return;
                }

                tronXCamera = new TronXCamera();

                tronXCamera.setName(cameraName);
                tronXCamera.setInternalHost(cameraInternalUrl);
                tronXCamera.setInternalHttp(cameraHttp);
                tronXCamera.setUsername(cameraUsername);
                tronXCamera.setPassword(cameraPassword);

                addDevice();

            }
        });
    }

    private void createCamera(TronXCamera tronXCamera) {
        try {
            DbCamera dbCamera = new DbCamera(this);
            dbCamera.addCamera(tronXCamera);
            finish();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Log.e(TAG, "add camera to TThings: " + e.getMessage());
        }
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse onvifResponse) {

    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {
        Toast.makeText(Add_Camera_Manually.this, "Device Not Added", Toast.LENGTH_SHORT).show();
    }

    private void addDevice(){

        /*if(devurl==null)
        {
            Toast.makeText(Add_Camera_Manually.this, "Enter Device IP or Search for devices", Toast.LENGTH_SHORT).show();
            return;
        }*/

        onvifDevice = new OnvifDevice("http://"+cameraInternalUrl+":"+cameraHttp, "admin", "admin");
        onvifManager.getServices(onvifDevice, new OnvifServiceListener() {
            @Override
            public void onServicesReceived(OnvifDevice onvifDevice, OnvifServices onvifServices) {

                if (onvifServices != null) {
                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Add_Camera_Manually.this, "Device added successfully", Toast.LENGTH_SHORT).show();
                            createCamera(tronXCamera);
                            //getServices();
                        }
                    });
                }
                else
                {
                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Add_Camera_Manually.this, "Device Not Added", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        //onvifDevice = new OnvifDevice(deviceUri.getText().toString(), deviceuser.getText().toString(), devicepassword.getText().toString());
    }

    private void getServices() {

        if(onvifDevice == null)
        {
            Toast.makeText(Add_Camera_Manually.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager.getServices(onvifDevice, new OnvifServiceListener() {
            @Override
            public void onServicesReceived(OnvifDevice onvifDevice, OnvifServices onvifServices) {
                if (onvifServices != null) {
                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Add_Camera_Manually.this,"Service : "+onvifServices.getServicepath()+"\n"
                                    +"Device : "+onvifServices.getDeviceinfomationpath()+"\n"
                                    +"Profile : "+onvifServices.getProfilespath()+"\n"
                                    +"Stream : "+onvifServices.getStreamURIpath(), Toast.LENGTH_SHORT).show();
                            getDeviceInformation();
                        }



                    });
                }
                else
                {
                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Add_Camera_Manually.this, "No Servcies Found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getDeviceInformation() {
        if(onvifDevice == null)
        {
            Toast.makeText(Add_Camera_Manually.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }
        onvifManager.getDeviceInformation(onvifDevice, new OnvifDeviceInformationListener() {
            @Override
            public void onDeviceInformationReceived(OnvifDevice onvifDevice, OnvifDeviceInformation onvifDeviceInformation) {

                if (onvifDeviceInformation != null) {
                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Add_Camera_Manually.this, "Model :: "+onvifDeviceInformation.getModel()+"\n"
                                    +"Manufacturer :: "+onvifDeviceInformation.getManufacturer()+"\n"
                                    +"Serial Number :: "+onvifDeviceInformation.getSerialNumber()+"\n"
                                    +"Hardware ID :: "+onvifDeviceInformation.getHardwareId(), Toast.LENGTH_SHORT).show();
                            getProfiles();
                        }
                    });
                }
                else
                {
                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Add_Camera_Manually.this, "Device Info Not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void getProfiles() {

        if(onvifDevice == null)
        {
            Toast.makeText(Add_Camera_Manually.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }

        onvifManager.getMediaProfiles(onvifDevice, new DeviceMediaProfileListener() {
            @Override
            public void onMediaProfileReceived(OnvifDevice onvifDevice, List<DeviceMediaProfile> mediaProfiles) {
                if (mediaProfiles != null) {

                    onvifDevice.addProfiles(mediaProfiles);
                    for (DeviceMediaProfile profile : onvifDevice.getProfiles()) {

                        Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Add_Camera_Manually.this,""+profile.getToken(), Toast.LENGTH_SHORT).show();
                                getStreamUri();
                            }
                        });

                        onvifManager.getMediaStreamURI(onvifDevice, profile, new OnvifStreamUriListener() {
                            @Override
                            public void onvifStreamUriReceived(OnvifDevice onvifDevice, DeviceMediaProfile onvifMediaProfile, String s) {
                                if (s != null) {
                                    String profile = onvifMediaProfile.getName();
                                    streamuri = s;
                                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(Add_Camera_Manually.this,"Stream URI ::"+s, Toast.LENGTH_SHORT).show();
                                            tronXCamera.setProfile(profile);
                                            tronXCamera.setRtspurl(streamuri);

                                            //mediaProfile.setRtspUrl(streamuri);

                                            createCamera(tronXCamera);
                                            //play();

                                        }
                                    });
                                }
                                else
                                {
                                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(Add_Camera_Manually.this, "URI Not found", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });

                    }


                }
                else
                {
                    Add_Camera_Manually.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Add_Camera_Manually.this, "Profiles Not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        /*onvifManager.getMediaProfiles(onvifDevice, new OnvifMediaProfileListener() {
            @Override
            public void onMediaProfileReceived(OnvifDevice onvifDevice, List<OnvifMediaProfile> list) {
                mediaProfile=list.get(0);
                if (list != null) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this,""+list.get(0).getToken()+"\n"+list.get(1).getToken(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "Profiles Not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });*/
    }

    private void getStreamUri() {

        if(onvifDevice == null)
        {
            Toast.makeText(Add_Camera_Manually.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private void play() {
        if(onvifDevice == null)
        {
            Toast.makeText(Add_Camera_Manually.this, "Please add the Device first", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Add_Camera_Manually.this, TronXPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("streamUri", streamuri);
        bundle.putString("profile", profile);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
