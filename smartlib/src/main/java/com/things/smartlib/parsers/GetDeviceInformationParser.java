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
    public static final String TAG = GetDeviceInformationParser.class.getSimpleName();
    private static final String KEY_MANUFACTURER = "Manufacturer";//IDT
    private static final String KEY_MODEL = "Model";//IPC100
    private static final String KEY_FIRMWARE_VERSION = "FirmwareVersion";//81.8.4.0152
    private static final String KEY_SERIAL_NUMBER = "SerialNumber";//14070837AD61
    private static final String KEY_HARDWARE_ID = "HardwareId";//001

    private static final String xmlResponse="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:wsa5=\"http://www.w3.org/2005/08/addressing\" xmlns:c14n=\"http://www.w3.org/2001/10/xml-exc-c14n#\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:xmime5=\"http://www.w3.org/2005/05/xmlmime\" xmlns:xop=\"http://www.w3.org/2004/08/xop/include\" xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" xmlns:tt=\"http://www.onvif.org/ver10/schema\" xmlns:wstop=\"http://docs.oasis-open.org/wsn/t-1\" xmlns:wsrfbf=\"http://docs.oasis-open.org/wsrf/bf-2\" xmlns:d=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\" xmlns:wsrfr=\"http://docs.oasis-open.org/wsrf/r-2\" xmlns:remdb=\"http://www.onvif.org/ver10/network/wsdl/RemoteDiscoveryBinding\" xmlns:dislb=\"http://www.onvif.org/ver10/network/wsdl/DiscoveryLookupBinding\" xmlns:dn=\"http://www.onvif.org/ver10/network/wsdl\" xmlns:pulpsb=\"http://www.onvif.org/ver10/events/wsdl/PullPointSubscriptionBinding\" xmlns:eveb=\"http://www.onvif.org/ver10/events/wsdl/EventBinding\" xmlns:tev=\"http://www.onvif.org/ver10/events/wsdl\" xmlns:submb=\"http://www.onvif.org/ver10/events/wsdl/SubscriptionManagerBinding\" xmlns:notpb=\"http://www.onvif.org/ver10/events/wsdl/NotificationProducerBinding\" xmlns:notcb=\"http://www.onvif.org/ver10/events/wsdl/NotificationConsumerBinding\" xmlns:pulpb=\"http://www.onvif.org/ver10/events/wsdl/PullPointBinding\" xmlns:creppb=\"http://www.onvif.org/ver10/events/wsdl/CreatePullPointBinding\" xmlns:pausmb=\"http://www.onvif.org/ver10/events/wsdl/PausableSubscriptionManagerBinding\" xmlns:wsnt=\"http://docs.oasis-open.org/wsn/b-2\" xmlns:tds=\"http://www.onvif.org/ver10/device/wsdl\" xmlns:tmd=\"http://www.onvif.org/ver10/deviceIO/wsdl\" xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\" xmlns:trt=\"http://www.onvif.org/ver10/media/wsdl\"><soap:Body><tds:GetDeviceInformationResponse><tds:Manufacturer></tds:Manufacturer><tds:Model></tds:Model><tds:FirmwareVersion></tds:FirmwareVersion><tds:SerialNumber></tds:SerialNumber><tds:HardwareId></tds:HardwareId></tds:GetDeviceInformationResponse></soap:Body></soap:Envelope>";

    @Override
    public OnvifDeviceInformation parse(OnvifResponse response) {
        OnvifDeviceInformation deviceInformation = new OnvifDeviceInformation();

        try {
            //Set the input for parser.
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            //getXmlPullParser().setInput(new StringReader(xmlResponse));
            //Returns the type of the current event (START_TAG, END_TAG, TEXT, etc.)
            eventType = getXmlPullParser().getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_MANUFACTURER)) {
                    //Get next parsing event -
                    // element content will be coalesced and only one TEXT event must be returned for whole element content
                    // (comments and processing instructions will be ignored and entity references must be expanded or
                    // exception must be thrown if entity reference can not be expanded).
                    getXmlPullParser().next();
                    //Read text content of the current event as String.
                    //set to Manufacturer of device information.
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

