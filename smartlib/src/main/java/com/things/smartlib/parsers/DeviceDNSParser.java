package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceDNS;
import com.things.smartlib.models.DeviceDiscoveryMode;
import com.things.smartlib.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.parsers
 * @date :         11/6/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceDNSParser extends OnvifParser<DeviceDNS>{

    private static final String TAG = DeviceDNSParser.class.getSimpleName();
    private static final String KEY_FROMDHCP = "FromDHCP";
    private static final String KEY_DNS_TYPE = "Type";
    private static final String KEY_DNS_IP = "IPv4Address";

    @Override
    public DeviceDNS parse(OnvifResponse response) {

        DeviceDNS deviceDNS = new DeviceDNS();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_FROMDHCP))
                {
                    getXmlPullParser().next();
                    deviceDNS.setFromdhcp(getXmlPullParser().getText());
                }
                else if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_DNS_TYPE))
                {
                    getXmlPullParser().next();
                    deviceDNS.setDnstype(getXmlPullParser().getText());
                }
                else if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_DNS_IP))
                {
                    getXmlPullParser().next();
                    deviceDNS.setDnstype(getXmlPullParser().getText());
                }

                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceDNS;
    }
}
