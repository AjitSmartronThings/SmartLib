package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceNWInterfaces;
import com.things.smartlib.models.DeviceNWProtocols;
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
public class DeviceNWProtocolsParser extends OnvifParser<DeviceNWProtocols>{

    private static final String TAG = DeviceNWProtocolsParser.class.getSimpleName();
    //Name,Enabled,Port
    private static final String KEY_PROTOCOL_NAME = "Name";
    private static final String KEY_IS_ENABLED = "Enabled";
    private static final String KEY_PORT = "Port";

    @Override
    public DeviceNWProtocols parse(OnvifResponse response) {

        DeviceNWProtocols nwProtocols = new DeviceNWProtocols();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_PROTOCOL_NAME))
                {
                    getXmlPullParser().next();
                    nwProtocols.setName(getXmlPullParser().getText());
                }
                else if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_IS_ENABLED))
                {
                    getXmlPullParser().next();
                    nwProtocols.setIsenabled(getXmlPullParser().getText());
                } else if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_PORT))
                {
                    getXmlPullParser().next();
                    nwProtocols.setPort(getXmlPullParser().getText());
                }

                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nwProtocols;
    }
}
