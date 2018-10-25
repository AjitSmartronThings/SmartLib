package com.things.smartlib.parsers;

import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Get media profiles parser.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.parsers
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetMediaProfilesParser extends OnvifParser<List<OnvifMediaProfile>> {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = GetMediaProfilesParser.class.getSimpleName();
    private static final String KEY_PROFILES = "Profiles";
    private static final String ATTR_TOKEN = "token";
    private static final String ATTR_NAME = "Name";

    @Override
    public List<OnvifMediaProfile> parse(OnvifResponse response) {
        List<OnvifMediaProfile> profiles = new ArrayList<>();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_PROFILES)) {

                    String token = getXmlPullParser().getAttributeValue(null, ATTR_TOKEN);
                    getXmlPullParser().nextTag();
                    if (getXmlPullParser().getName().equals(ATTR_NAME)) {
                        getXmlPullParser().next();
                        String name = getXmlPullParser().getText();
                        profiles.add(new OnvifMediaProfile(name, token));
                    }
                }
                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return profiles;
    }

}

