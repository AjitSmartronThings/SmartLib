package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceMediaProfile;
import com.things.smartlib.models.OnvifMediaProfile;
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
 * @date :         11/19/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceMediaProfileParser extends OnvifParser<List<DeviceMediaProfile>>{

    private static final String KEY_PROFILES = "Profiles";
    private static final String ATTR_TOKEN = "token";
    private static final String ATTR_NAME = "Name";
    private static final String ATTR_VIDEO_ENCODER_CONFIGURATION = "VideoEncoderConfiguration";
    private static final String ATTR_AUDIO_ENCODER_CONFIGURATION = "AudioEncoderConfiguration";
    private static final String ATTR_PTZ_CONFIGURATION = "PTZConfiguration";
    private static final String VIDEO_WIDTH = "Width";
    private static final String VIDEO_HEIGHT = "Height";
    private static final String VIDEO_FRAME_RATE_LIMIT = "FrameRateLimit";
    private static final String ATTR_ENCODING = "Encoding";
    private static final String ATTR_BITRATE = "Bitrate";
    private static final String ATTR_SAMPLE_RATE = "SampleRate";

    @Override
    public List<DeviceMediaProfile> parse(OnvifResponse response) {

        List<DeviceMediaProfile> profiles = new ArrayList<>();
        DeviceMediaProfile profile = null;
        String tag = "";
        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        //serviceUrl
                        if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_PROFILES)) {
                            profile = new DeviceMediaProfile();
                            String token = getXmlPullParser().getAttributeValue(null, ATTR_TOKEN);
                            profile.setToken(token);
                            getXmlPullParser().nextTag();
                            if (getXmlPullParser().getName().equals(ATTR_NAME)) {
                                getXmlPullParser().next();
                                String name = getXmlPullParser().getText();
                                profile.setName(name);
                                //profiles.add(new OnvifMediaProfile(name, token));
                            }
                        }
                         else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(ATTR_VIDEO_ENCODER_CONFIGURATION) && profile != null) {
                            profile.getVideoEncode().setToken(getXmlPullParser().getAttributeValue(0));
                            tag = "Video";
                        } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(ATTR_AUDIO_ENCODER_CONFIGURATION) && profile != null) {
                            profile.getAudioEncode().setToken(getXmlPullParser().getAttributeValue(0));
                            tag = "Audio";
                        } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(VIDEO_WIDTH) && profile != null) {
                            String text = getXmlPullParser().nextText();
                            if (tag.equals("Video")) {
                                profile.getVideoEncode().setWidth(Integer.parseInt(text));
                            }
                        } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(VIDEO_HEIGHT) && profile != null) {
                            String text = getXmlPullParser().nextText();
                            if (tag.equals("Video")) {
                                profile.getVideoEncode().setHeight(Integer.parseInt(text));
                            }
                        } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(VIDEO_FRAME_RATE_LIMIT) && profile != null) {
                            String text = getXmlPullParser().nextText();
                            if (tag.equals("Video")) {
                                profile.getVideoEncode().setFrameRate(Integer.parseInt(text));
                            }
                        } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(ATTR_ENCODING) && profile != null) {
                            String text = getXmlPullParser().nextText();
                            if (tag.equals("Video")) {
                                profile.getVideoEncode().setEncoding(text);
                            } else if (tag.equals("Audio")) {
                                profile.getAudioEncode().setEncoding(text);
                            }
                        } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(ATTR_BITRATE) && profile != null) {
                            //Bitrate
                            String text = getXmlPullParser().nextText();
                            if (tag.equals("Audio")) {
                                profile.getAudioEncode().setBitrate(Integer.parseInt(text));
                            }
                        } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(ATTR_SAMPLE_RATE) && profile != null) {
                            //SampleRate
                            String text = getXmlPullParser().nextText();
                            if (tag.equals("Audio")) {
                                profile.getAudioEncode().setSampleRate(Integer.parseInt(text));
                            }
                        } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(ATTR_PTZ_CONFIGURATION) && profile != null) {
                            String token = getXmlPullParser().getAttributeValue(null, ATTR_TOKEN);
                            profile.getPtzConfiguration().setToken(token);
                            tag = "Ptz";
                            getXmlPullParser().nextTag();
                            if (getXmlPullParser().getName().equals(ATTR_NAME)) {
                                getXmlPullParser().next();
                                String name = getXmlPullParser().getText();
                                if (tag.equals("Ptz")) {
                                    profile.getPtzConfiguration().setNodeToken(name);
                                }
                                //profiles.add(new OnvifMediaProfile(name, token));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (getXmlPullParser().getName().equals(KEY_PROFILES)) {
                            profiles.add(profile);
                        }
                        if (getXmlPullParser().getName().equals(ATTR_AUDIO_ENCODER_CONFIGURATION)
                                || getXmlPullParser().getName().equals(ATTR_VIDEO_ENCODER_CONFIGURATION) || getXmlPullParser().getName().equals("PTZConfiguration")) {
                            tag = "";
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

        return profiles;
    }
}
