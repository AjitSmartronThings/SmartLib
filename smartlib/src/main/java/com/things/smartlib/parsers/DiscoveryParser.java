package com.things.smartlib.parsers;

import android.util.Log;

import com.things.smartlib.DiscoveryMode;
import com.things.smartlib.OnvifUtils;
import com.things.smartlib.models.Device;
import com.things.smartlib.models.DiscoveryType;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.UPnPDevice;
import com.things.smartlib.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.parsers
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DiscoveryParser extends OnvifParser<List<Device>> {
    //Constants
    public static final String TAG = DiscoveryParser.class.getSimpleName();
    private static final String LINE_END = "\r\n";
    private static String KEY_UPNP_LOCATION = "LOCATION: ";
    private static String KEY_UPNP_SERVER = "SERVER: ";
    private static String KEY_UPNP_USN = "USN: ";
    private static String KEY_UPNP_ST = "ST: ";

    //Attributes
    private DiscoveryMode mode;
    private String hostName;
    private String port;
    private String deviceUrl;

    //Constructors
    public DiscoveryParser(DiscoveryMode mode) {
        this.mode = mode;
        hostName = "";
    }
    @Override
    public List<Device> parse(OnvifResponse response) {
        List<Device> devices = new ArrayList<>();

        switch (mode) {
            case ONVIF:
                devices.addAll(parseOnvif(response));
                break;
            case UPNP:
                devices.add(parseUPnP(response));
                break;
        }

        return devices;
    }

    private List<Device> parseOnvif(OnvifResponse response) {
        ArrayList<Device> devices = new ArrayList<>();
        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals("Types")) {
                    getXmlPullParser().next();
                    String type = getXmlPullParser().getText();

                    if (mode.equals(DiscoveryMode.ONVIF) && type.contains(DiscoveryType.NETWORK_VIDEO_TRANSMITTER.type)) {
                        String uri = OnvifUtils.retrieveXAddrs(getXmlPullParser());
                        devices.addAll(parseDevicesFromUri(uri));
                    }
                }

                eventType = getXmlPullParser().next();
            }
            Log.d("", ""+devices);
            return devices;
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Device parseUPnP(OnvifResponse response) {
        String header = response.getXml();
        String location = parseUPnPHeader(header, KEY_UPNP_LOCATION);
        String server = parseUPnPHeader(header, KEY_UPNP_SERVER);
        String usn = parseUPnPHeader(header, KEY_UPNP_USN);
        String st = parseUPnPHeader(header, KEY_UPNP_ST);
        return new UPnPDevice(getHostName(), header, location, server, usn, st);
    }

    private List<OnvifDevice> parseDevicesFromUri(String uri) {
        List<OnvifDevice> devices = new ArrayList<>();
        String[] uris = uri.split("\\s+");
        for (String address : uris) {
            //OnvifDevice device = new OnvifDevice(getHostName());
            OnvifDevice device = new OnvifDevice(getDeviceUrl());
            device.addAddress(address);
            devices.add(device);
        }

        return devices;
    }

    private String parseUPnPHeader(String header, String whatSearch) {
        String result = "";
        int searchLinePos = header.indexOf(whatSearch);
        if (searchLinePos != -1) {
            searchLinePos += whatSearch.length();
            int locColon = header.indexOf(LINE_END, searchLinePos);
            result = header.substring(searchLinePos, locColon);
        }
        return result;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public String getDeviceUrl() {
        return deviceUrl;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDeviceUrl() {
        this.deviceUrl = getHostName() + ":36000" ;
    }
}
