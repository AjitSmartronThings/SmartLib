package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceScopes;
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
public class DeviceScopesParser extends OnvifParser<DeviceScopes>{

    private static final String TAG = DeviceScopesParser.class.getSimpleName();
//ScopeDef,ScopeItem
    private static final String KEY_SCOPEDEF = "ScopeDef";
    private static final String KEY_SCOPEITEM = "ScopeItem";

    @Override
    public DeviceScopes parse(OnvifResponse response) {

        DeviceScopes deviceScopes = new DeviceScopes();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));

            eventType = getXmlPullParser().getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_SCOPEDEF))
                {
                    getXmlPullParser().next();
                    deviceScopes.setScopedef(getXmlPullParser().getText());
                }else if(eventType != XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_SCOPEITEM))
                {
                    getXmlPullParser().next();
                    deviceScopes.setScopeitem(getXmlPullParser().getText());
                }

                eventType = getXmlPullParser().next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceScopes;
    }
}
