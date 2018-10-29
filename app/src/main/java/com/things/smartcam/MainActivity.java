package com.things.smartcam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.things.smartlib.OnvifManager;
import com.things.smartlib.listeners.OnvifDeviceInformationListener;
import com.things.smartlib.listeners.OnvifMediaProfileListener;
import com.things.smartlib.listeners.OnvifPTZListener;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.listeners.OnvifServiceListener;
import com.things.smartlib.listeners.OnvifStreamUriListener;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifDeviceInformation;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.models.OnvifServices;
import com.things.smartlib.models.PTZType;
import com.things.smartlib.responses.OnvifResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnvifResponseListener{


    private static final String TAG = MainActivity.class.getSimpleName();
    OnvifMediaProfile mediaProfile;
    int x,y,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting Device Services,Device Information,Device Profiles,Device Stream URI
        OnvifManager onvifManager=new OnvifManager();
        onvifManager.setOnvifResponseListener(this);
        OnvifDevice onvifDevice=new OnvifDevice("192.168.0.4:36000","admin","admin");
        onvifManager.getServices(onvifDevice, new OnvifServiceListener() {
            @Override
            public void onServicesReceived(OnvifDevice onvifDevice, OnvifServices path) {
                System.out.println(""+path.getServicepath());
                onvifManager.getDeviceInformation(onvifDevice, new OnvifDeviceInformationListener() {
                    @Override
                    public void onDeviceInformationReceived(OnvifDevice device, OnvifDeviceInformation deviceInformation) {
                        System.out.println(""+deviceInformation);
                        onvifManager.getMediaProfiles(device, new OnvifMediaProfileListener() {
                            @Override
                            public void onMediaProfileReceived(OnvifDevice onvifDevice, List<OnvifMediaProfile> mediaProfiles) {
                                mediaProfile=mediaProfiles.get(0);
                                System.out.println(""+mediaProfiles.get(0).getToken()+"\n"+mediaProfiles.get(1).getToken());
                                onvifManager.getMediaStreamURI(device, mediaProfile, new OnvifStreamUriListener() {
                                    @Override
                                    public void onvifStreamUriReceived(OnvifDevice onvifDevice, OnvifMediaProfile onvifMediaProfile, String uri) {
                                        System.out.println(""+uri);
                                        try {
                                       onvifManager.sendPTZRequest(device, mediaProfile, PTZType.LEFT_MOVE, new OnvifPTZListener() {
                                           @Override
                                           public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {

                                           }
                                       });
                                        Thread.sleep(10000);
                                            onvifManager.stopPTZRequest(device,mediaProfile);
                                            onvifManager.sendPTZRequest(device, mediaProfile, PTZType.RIGHT_MOVE, new OnvifPTZListener() {
                                                @Override
                                                public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {

                                                }
                                            });
                                            Thread.sleep(10000);
                                            onvifManager.stopPTZRequest(device,mediaProfile);
                                            onvifManager.sendPTZRequest(device, mediaProfile, PTZType.UP_MOVE, new OnvifPTZListener() {
                                                @Override
                                                public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {

                                                }
                                            });
                                            Thread.sleep(10000);
                                            onvifManager.stopPTZRequest(device,mediaProfile);
                                            onvifManager.sendPTZRequest(device, mediaProfile, PTZType.DOWN_MOVE, new OnvifPTZListener() {
                                                @Override
                                                public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {

                                                }
                                            });
                                            Thread.sleep(10000);
                                            onvifManager.stopPTZRequest(device,mediaProfile);
                                            onvifManager.sendPTZRequest(device, mediaProfile, PTZType.ZOOM_IN, new OnvifPTZListener() {
                                                @Override
                                                public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {

                                                }
                                            });
                                            Thread.sleep(10000);
                                            onvifManager.stopPTZRequest(device,mediaProfile);
                                            onvifManager.sendPTZRequest(device, mediaProfile, PTZType.ZOOM_OUT, new OnvifPTZListener() {
                                                @Override
                                                public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {

                                                }
                                            });
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse onvifResponse) {
        Log.e(TAG,onvifResponse.toString());
    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {
        Log.e(TAG,errorMessage);
    }
}
