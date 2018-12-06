package com.things.smartcam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.things.smartlib.FindDevicesThread;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.models.Device;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.responses.OnvifResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class LanSearch extends AppCompatActivity implements OnvifResponseListener,FindDevicesThread.FindDevicesListener{

    private ArrayList<Device> devices;
    private int selectedIndex = -1;

    ListView listView;

    private DevicesAdapter adapter;

    String devurl = null;

    ProgressDialog pg;

    String ipAddressString;
    String onvifPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lan_search);

        listView = (ListView) findViewById(R.id.listView1);
        devices = new ArrayList<>();
        adapter = new DevicesAdapter(this, devices);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                String ipAddress = devices.get(position).getHost();
                getIP(ipAddress);
                Bundle bundle = new Bundle();
                bundle.putString("ipaddress",ipAddressString);
                bundle.putString("ipport",onvifPort);
                Intent intent = new Intent(LanSearch.this,Add_Camera_Manually.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //new GetDeviceInfoThread(devices.get(position), MainActivity.this, MainActivity.this).start();
            }
        });

        searchDevices();
    }

    private void getIP(String ipAddress)
    {
        try {
            String[] urlArray = ipAddress.split("\\s");

            int httpPort;


            try {
                ipAddressString = "";
                httpPort = 0;
                String[] var11 = urlArray;
                int var18 = urlArray.length;

                for(int var17 = 0; var17 < var18; ++var17) {
                    String urlString = var11[var17];
                    URL localURL = new URL(urlString);
                    String urlHost = localURL.getHost();
                    if (IpTranslator.isLocalIpv4(urlHost)) {
                        ipAddressString = urlHost;
                        httpPort = localURL.getPort();

                        if (httpPort == -1) {
                            httpPort = 80;
                        }

                        onvifPort = String.valueOf(httpPort);
                        break;
                    }
                }

            } catch (MalformedURLException var14) {
            }
        } catch (Exception var15) {
        }
    }

    @Override
    public void searchResult(ArrayList<Device> devices) {
        this.devices.clear();
        this.devices.addAll(devices);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void searchDevices()
    {

        pg=new ProgressDialog(LanSearch.this);
        pg.show();
        new FindDevicesThread(this, this).start();
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse onvifResponse) {

    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {

    }
}
