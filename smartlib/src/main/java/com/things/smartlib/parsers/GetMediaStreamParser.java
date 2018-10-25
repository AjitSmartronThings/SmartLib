package com.things.smartlib.parsers;

import com.things.smartlib.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * The type Get media stream parser.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.parsers
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetMediaStreamParser extends OnvifParser<String> {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = GetMediaStreamParser.class.getSimpleName();
    private static final String KEY_URI = "Uri";

    @Override
    public String parse(OnvifResponse response) {
        String uri = "";
        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_URI)) {

                   getXmlPullParser().next();
                    uri = getXmlPullParser().getText();
                    break;
                }
                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return uri;
    }

}
