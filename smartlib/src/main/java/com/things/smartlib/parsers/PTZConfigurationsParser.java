package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceMediaProfile;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.models.PTZConfigurations;
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
 * @date :         11/27/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class PTZConfigurationsParser extends OnvifParser<List<PTZConfigurations>> {

    private static final String TAG = PTZConfigurationsParser.class.getSimpleName();
    private static final String KEY_PTZ_CONFIGURATIONS = "PTZConfiguration";
    private static final String KEY_PTZ_NODES = "PTZNode";
    private static final String ATTR_TOKEN = "token";
    private static final String ATTR_NAME = "Name";
    private static final String ATTR_NODETOKEN = "NodeToken";

    private static final String ATTR_ABSOLUTE_PAN_TILT_SPACE = "DefaultAbsolutePantTiltPositionSpace";
    private static final String ATTR_ABSOLUTE_ZOOM_SPACE = "DefaultAbsoluteZoomPositionSpace";
    private static final String ATTR_RELATIVE_PAN_TILT_SPACE = "DefaultRelativePanTiltTranslationSpace";
    private static final String ATTR_RELATIVE_ZOOM_SPACE = "DefaultRelativeZoomTranslationSpace";
    private static final String ATTR_CONTINUOUS_PAN_TILT_SPACE = "DefaultContinuousPanTiltVelocitySpace";
    private static final String ATTR_CONTINUOUS_ZOOM_SPACE = "DefaultContinuousZoomVelocitySpace";

    private static final String ATTR_DEFAULT_PTZ_SPEED = "DefaultPTZSpeed";
    private static final String ATTR_PAN_TILT = "PanTilt";
    private static final String ATTR_ZOOM = "Zoom";
    private static final String ATTR_SPACE = "space";
    private static final String ATTR_X = "x";
    private static final String ATTR_Y = "y";


    @Override
    public List<PTZConfigurations>  parse(OnvifResponse response) {
        List<PTZConfigurations> ptzConfigurations = new ArrayList<>();
        PTZConfigurations ptzConfiguration = null;
        String tag = "";
        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_PTZ_CONFIGURATIONS) || getXmlPullParser().getName().equals(KEY_PTZ_NODES)) {
                            String token = getXmlPullParser().getAttributeValue(null, ATTR_TOKEN);
                            ptzConfiguration.setToken(token);
                            tag = "Ptz";
                            getXmlPullParser().nextTag();
                            if (getXmlPullParser().getName().equals(ATTR_NAME)) {
                                getXmlPullParser().next();
                                String name = getXmlPullParser().getText();
                                if (tag.equals("Ptz")) {
                                    ptzConfiguration.setName(name);
                                }
                            }
                        }
                        else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(ATTR_NODETOKEN) && ptzConfiguration != null) {
                            String text = getXmlPullParser().nextText();
                            ptzConfiguration.setNodetoken(text);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (getXmlPullParser().getName().equals(KEY_PTZ_CONFIGURATIONS) || getXmlPullParser().getName().equals(KEY_PTZ_NODES)) {
                            ptzConfigurations.add(ptzConfiguration);
                        }
                        break;
                    default:
                        break;
                }
                eventType = getXmlPullParser().next();
            }
        }
        catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return ptzConfigurations;
    }
}
