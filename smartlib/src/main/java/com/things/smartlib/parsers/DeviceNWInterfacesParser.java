package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceNWGateway;
import com.things.smartlib.models.DeviceNWInterfaces;
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
public class DeviceNWInterfacesParser extends OnvifParser<DeviceNWInterfaces>{

    private static final String TAG = DeviceNWInterfacesParser.class.getSimpleName();
    //HwAddress,Address,DHCP
    private static final String KEY_HW_ADDRESS = "HwAddress";
    private static final String KEY_NW_ADDRESS = "Address";
    private static final String KEY_PREFIX_LENGTH = "PrefixLength";
    private static final String KEY_DHCP = "DHCP";

    @Override
    public DeviceNWInterfaces parse(OnvifResponse response) {

        DeviceNWInterfaces nwInterfaces = new DeviceNWInterfaces();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_HW_ADDRESS))
                {
                    getXmlPullParser().next();
                    nwInterfaces.setHwaddress(getXmlPullParser().getText());
                }
                else if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_NW_ADDRESS))
                {
                    getXmlPullParser().next();
                    nwInterfaces.setAddress(getXmlPullParser().getText());
                } else if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_PREFIX_LENGTH))
                {
                    getXmlPullParser().next();
                    nwInterfaces.setPrefixlength(getXmlPullParser().getText());
                }
                else if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_DHCP))
                {
                    getXmlPullParser().next();
                    nwInterfaces.setDhcp(getXmlPullParser().getText());
                }

                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nwInterfaces;
    }
}
