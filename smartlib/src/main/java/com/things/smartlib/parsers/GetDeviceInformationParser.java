package com.things.smartlib.parsers;

import com.things.smartlib.models.OnvifDeviceInformation;
import com.things.smartlib.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * The type Get device information parser.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.parsers
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetDeviceInformationParser extends OnvifParser<OnvifDeviceInformation> {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = GetDeviceInformationParser.class.getSimpleName();
    private static final String KEY_MANUFACTURER = "Manufacturer";
    private static final String KEY_MODEL = "Model";
    private static final String KEY_FIRMWARE_VERSION = "FirmwareVersion";
    private static final String KEY_SERIAL_NUMBER = "SerialNumber";
    private static final String KEY_HARDWARE_ID = "HardwareId";

    @Override
    public OnvifDeviceInformation parse(OnvifResponse response) {
        OnvifDeviceInformation deviceInformation = new OnvifDeviceInformation();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MANUFACTURER)) {
                    getXmlPullParser().next();
                    deviceInformation.setManufacturer(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MODEL)) {
                    getXmlPullParser().next();
                    deviceInformation.setModel(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_FIRMWARE_VERSION)) {
                    getXmlPullParser().next();
                    deviceInformation.setFirmwareVersion(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_SERIAL_NUMBER)) {
                    getXmlPullParser().next();
                    deviceInformation.setSerialNumber(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_HARDWARE_ID)) {
                    getXmlPullParser().next();
                    deviceInformation.setHardwareId(getXmlPullParser().getText());
                }
                eventType = getXmlPullParser().next();

            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return deviceInformation;
    }

}

