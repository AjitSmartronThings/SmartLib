package com.things.smartlib.parsers;

import com.things.smartlib.responses.OnvifResponse;
import com.things.smartlib.upnp.UPnPDeviceInformation;

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
 * @date :         11/1/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class UPnPParser extends OnvifParser<UPnPDeviceInformation> {

    //Constants
    public static final String TAG = UPnPParser.class.getSimpleName();
    private static final String KEY_DEVICE_TYPE = "deviceType";
    private static final String KEY_FRIENDLY_NAME = "friendlyName";
    private static final String KEY_MANUFACTURER = "manufacturer";
    private static final String KEY_MANUFACTURER_URL = "manufacturerURL";
    private static final String KEY_MODEL_DESCRIPTION = "modelDescription";
    private static final String KEY_MODEL_NAME = "modelName";
    private static final String KEY_MODEL_NUMBER = "modelNumber";
    private static final String KEY_MODEL_URL = "modelURL";
    private static final String KEY_SERIAL_NUMBER = "serialNumber";
    private static final String KEY_UDN = "UDN";
    private static final String KEY_PRESENTATION_URL = "presentationURL";
    private static final String KEY_URL_BASE = "URLBase";

    //Attributes

    @Override
    public UPnPDeviceInformation parse(OnvifResponse response) {
        UPnPDeviceInformation deviceInformation = new UPnPDeviceInformation();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_DEVICE_TYPE)) {
                    getXmlPullParser().next();
                    deviceInformation.setDeviceType(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_FRIENDLY_NAME)) {
                    getXmlPullParser().next();
                    deviceInformation.setFriendlyName(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MANUFACTURER)) {
                    getXmlPullParser().next();
                    deviceInformation.setManufacturer(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MANUFACTURER_URL)) {
                    getXmlPullParser().next();
                    deviceInformation.setManufacturerURL(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MODEL_DESCRIPTION)) {
                    getXmlPullParser().next();
                    deviceInformation.setModelDescription(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MODEL_NAME)) {
                    getXmlPullParser().next();
                    deviceInformation.setModelName(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MODEL_NUMBER)) {
                    getXmlPullParser().next();
                    deviceInformation.setModelNumber(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MODEL_URL)) {
                    getXmlPullParser().next();
                    deviceInformation.setModelURL(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_SERIAL_NUMBER)) {
                    getXmlPullParser().next();
                    deviceInformation.setSerialNumber(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_UDN)) {
                    getXmlPullParser().next();
                    deviceInformation.setUDN(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_PRESENTATION_URL)) {
                    getXmlPullParser().next();
                    deviceInformation.setPresentationURL(getXmlPullParser().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_URL_BASE)) {
                    getXmlPullParser().next();
                    deviceInformation.setUrlBase(getXmlPullParser().getText());
                }

                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return deviceInformation;
    }
}
