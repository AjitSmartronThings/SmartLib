package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceHostname;
import com.things.smartlib.models.DeviceNWGateway;
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
public class DeviceNWGatewayParser extends OnvifParser<DeviceNWGateway>{

    private static final String TAG = DeviceNWGatewayParser.class.getSimpleName();
    private static final String KEY_NWGATEWAY_ADDRESS = "IPv4Address";

    @Override
    public DeviceNWGateway parse(OnvifResponse response) {

        DeviceNWGateway nwGateway = new DeviceNWGateway();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_NWGATEWAY_ADDRESS))
                {
                    getXmlPullParser().next();
                    nwGateway.setGatewayaddress(getXmlPullParser().getText());
                }

                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nwGateway;
    }
}
