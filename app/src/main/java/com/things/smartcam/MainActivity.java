package com.things.smartcam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.things.smartlib.OnvifManager;
import com.things.smartlib.listeners.DeviceDiscoverModeListener;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.models.DeviceDNS;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.responses.OnvifResponse;

public class MainActivity extends AppCompatActivity implements OnvifResponseListener{


    private static final String TAG = MainActivity.class.getSimpleName();
    OnvifMediaProfile mediaProfile;
    int x,y,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* DiscoveryManager manager = new DiscoveryManager();
        manager.setDiscoveryTimeout(10000);
        manager.discover(new DiscoveryListener() {
            @Override
            public void onDiscoveryStarted() {
                System.out.println("Discovery started");
            }

            @Override
            public void onDevicesFound(List<Device> devices) {
                for (Device device : devices)
                    System.out.println("Devices found: " + device.getHost());
            }
        });*/

       OnvifManager onvifManager=new OnvifManager();
       onvifManager.setOnvifResponseListener(this);
        OnvifDevice onvifDevice=new OnvifDevice("192.168.0.3:36000","admin","admin");


        //Getting Device Services,Device Information,Device Profiles,Device Stream URI
       /* OnvifManager onvifManager=new OnvifManager();
        onvifManager.setOnvifResponseListener(this);
        OnvifDevice onvifDevice=new OnvifDevice("192.168.0.5:36000","admin","admin");
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
                                        if (uri == null) {
                                            Toast.makeText(MainActivity.this, "Please choose a device and get streamUri", Toast.LENGTH_SHORT).show();
                                        } else {
                                            //String[] streamUri = new String[streamUriArray.size()];
                                            //String[] hxwArr = new String[streamUriArray.size()];
                                            //String[] profilearr=new String[onvifMediaProfile];
                                            *//*for (int i = 0; i < streamUriArray.size(); i++) {
                                                streamUri[i] = streamUriArray.get(i).getRtspUrl();
                                                hxwArr[i] = streamUriArray.get(i).getVideoEncode().getWidth() + " X " +streamUriArray.get(i).getVideoEncode().getHeight();
                                                profilearr[i]=streamUriArray.get(i).getName();
                                            }*//*

                                            Intent intent = new Intent(MainActivity.this, TronXPlayer.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("streamUri", uri);
                                            bundle.putString("profile", onvifMediaProfile.getName());
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }






                                      *//* onvifManager.sendPTZRequest(device, mediaProfile, PTZType.LEFT_MOVE, new OnvifPTZListener() {
                                           @Override
                                           public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {
                                               System.out.println(status);
                                               try {
                                                   Thread.sleep(10000);

                                                   onvifManager.sendPTZRequest(device, mediaProfile, PTZType.RIGHT_MOVE, new OnvifPTZListener() {
                                                       @Override
                                                       public void onPTZReceived(OnvifDevice onvifDevice, boolean status) {
                                                       }
                                                   });
                                               } catch (InterruptedException e) {
                                                   e.printStackTrace();
                                               }
                                           }
                                       });
*//*



                                            *//*Thread.sleep(10000);
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
                                            });*//*
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });*/
//Device Descover Mode
        onvifManager.getDeviceDiscoveryMode(onvifDevice, new DeviceDiscoverModeListener() {
            @Override
            public void OnDeviceDiscoverModeReceived(OnvifDevice onvifDevice, DeviceDNS deviceDiscoveryMode) {
                System.out.println(""+deviceDiscoveryMode.getDiscoverymode());
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
