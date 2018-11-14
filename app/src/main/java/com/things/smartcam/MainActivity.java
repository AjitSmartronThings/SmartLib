package com.things.smartcam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.things.smartlib.DiscoveryManager;
import com.things.smartlib.OnvifManager;
import com.things.smartlib.listeners.DiscoveryListener;
import com.things.smartlib.listeners.OnvifDeviceInformationListener;
import com.things.smartlib.listeners.OnvifMediaProfileListener;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.listeners.OnvifServiceListener;
import com.things.smartlib.listeners.OnvifStreamUriListener;
import com.things.smartlib.models.Device;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifDeviceInformation;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.models.OnvifServices;
import com.things.smartlib.player.TronXPlayer;
import com.things.smartlib.responses.OnvifResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnvifResponseListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    OnvifMediaProfile mediaProfile;
    OnvifManager onvifManager;
    OnvifDevice onvifDevice;
    String profile,streamuri;
    String devurl = null;

    AppCompatEditText deviceUri,deviceuser,devicepassword;
    AppCompatButton searchDevices,addDevice,getServices,getDeviceInformation,getProfiles,getStreamUri,play,multiPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceUri = (AppCompatEditText) findViewById(R.id.deviceUri);
        deviceuser = (AppCompatEditText) findViewById(R.id.deviceuser);
        devicepassword = (AppCompatEditText) findViewById(R.id.devicepassword);

        searchDevices = (AppCompatButton) findViewById(R.id.searchDevices);
        addDevice = (AppCompatButton) findViewById(R.id.addDevice);
        getServices = (AppCompatButton) findViewById(R.id.getServices);
        getDeviceInformation = (AppCompatButton) findViewById(R.id.getDeviceInformation);
        getProfiles = (AppCompatButton) findViewById(R.id.getProfiles);
        getStreamUri = (AppCompatButton) findViewById(R.id.getStreamUri);
        play = (AppCompatButton) findViewById(R.id.play);
        multiPlayer = (AppCompatButton) findViewById(R.id.multiPlayer);

        onvifManager = new OnvifManager();
        onvifManager.setOnvifResponseListener(this);

        searchDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDevices();
            }
        });

        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addDevice();
            }
        });

        getServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServices();
            }
        });

        getDeviceInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceInformation();
            }
        });

        getProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfiles();
            }
        });

        getStreamUri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStreamUri();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        multiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

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
       /* onvifManager.getDeviceDiscoveryMode(onvifDevice, new DeviceDiscoverModeListener() {
            @Override
            public void OnDeviceDiscoverModeReceived(OnvifDevice onvifDevice, DeviceDiscoveryMode deviceDiscoveryMode) {
                System.out.println("" + deviceDiscoveryMode.getDiscoverymode());
            }
        });*/
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse onvifResponse) {
        Log.e(TAG,onvifResponse.toString());
    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {
        Log.e(TAG,errorMessage);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchDevices()
    {
        ProgressDialog pg=new ProgressDialog(MainActivity.this);
        pg.show();
        DiscoveryManager manager = new DiscoveryManager();
        manager.setDiscoveryTimeout(15000);
        manager.discover(new DiscoveryListener() {
            @Override
            public void onDiscoveryStarted() {
                System.out.println("Discovery started");
            }

            @Override
            public void onDevicesFound(List<Device> devices) {

                for (Device device : devices) {
                    System.out.println("Devices found: " + device);
                    devurl = device.getHost();
                }

            }

            @Override
            public void onDiscoveryCompleted() {
                System.out.println("Discovery completed");
                pg.dismiss();
                deviceUri.setText(devurl);
                deviceuser.setText("admin");
                devicepassword.setText("admin");
            }
        });
    }

    private void addDevice(){
        onvifDevice = new OnvifDevice("192.168.0.5:36000", "admin", "admin");
        onvifManager.getServices(onvifDevice, new OnvifServiceListener() {
            @Override
            public void onServicesReceived(OnvifDevice onvifDevice, OnvifServices onvifServices) {

                if (onvifServices != null) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "Device added successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "Device Not Added", Toast.LENGTH_SHORT).show();
                    }
                });
                }

            }
        });
        //onvifDevice = new OnvifDevice(deviceUri.getText().toString(), deviceuser.getText().toString(), devicepassword.getText().toString());
    }

    private void getServices() {
        onvifManager.getServices(onvifDevice, new OnvifServiceListener() {
            @Override
            public void onServicesReceived(OnvifDevice onvifDevice, OnvifServices onvifServices) {
                if (onvifServices != null) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this,"Service : "+onvifServices.getServicepath()+"\n"
                            +"Device : "+onvifServices.getDeviceinfomationpath()+"\n"
                                    +"Profile : "+onvifServices.getProfilespath()+"\n"
                                    +"Stream : "+onvifServices.getStreamURIpath(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "No Servcies Found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getDeviceInformation() {
        onvifManager.getDeviceInformation(onvifDevice, new OnvifDeviceInformationListener() {
            @Override
            public void onDeviceInformationReceived(OnvifDevice onvifDevice, OnvifDeviceInformation onvifDeviceInformation) {

                if (onvifDeviceInformation != null) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "Model :: "+onvifDeviceInformation.getModel()+"\n"
                           +"Manufacturer :: "+onvifDeviceInformation.getManufacturer()+"\n"
                           +"Serial Number :: "+onvifDeviceInformation.getSerialNumber()+"\n"
                                    +"Hardware ID :: "+onvifDeviceInformation.getHardwareId(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "Device Info Not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void getProfiles() {
        onvifManager.getMediaProfiles(onvifDevice, new OnvifMediaProfileListener() {
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
        });
    }

    private void getStreamUri() {
        onvifManager.getMediaStreamURI(onvifDevice, mediaProfile, new OnvifStreamUriListener() {
            @Override
            public void onvifStreamUriReceived(OnvifDevice onvifDevice, OnvifMediaProfile onvifMediaProfile, String s) {
                if (s != null) {
                    profile = onvifMediaProfile.getName();
                    streamuri = s;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this,"Stream URI ::"+s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "URI Not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void play() {
        Intent intent = new Intent(MainActivity.this, TronXPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("streamUri", streamuri);
        bundle.putString("profile", profile);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
