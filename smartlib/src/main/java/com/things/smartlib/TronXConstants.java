package com.things.smartlib;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib
 * @date :         10/31/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class TronXConstants {

    //Constants Numbers
    public static final int ZERO = 0;
    public static final int ONE = 1;
    //Device Type
    public static final String ONVIF = "ONVIF";
    public static final String UPNP = "UPNP";
    //Discovery Mode
    public static final int DM_ONVIF = 3702;
    public static final int DM_UPNP = 1900;
    //Discovery Type
    public static final String DT_DEVICE = "Device";
    public static final String DT_NW_VIDEO_TRANSMITTER = "NetworkVideoTransmitter";
    //Discovery
    public static final String MULTICAST_ADDRESS_IPV4 = "239.255.255.250"; // Simple Service Discovery Protocol
    public static final String MULTICAST_ADDRESS_IPV6 = "[FF02::C]";
    public static int DISCOVERY_TIMEOUT = 15000;
    //Client
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int WRITE_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 10000;
    public static final String CONNECTION_MEDIATYPE = "application/soap+xml; charset=utf-8;";
    //WSDL URL
    public static final String WSDL_SERVICE = "http://www.onvif.org/ver10/device/wsdl";
    public static final String WSDL_DEVICE = "http://www.onvif.org/ver10/device/wsdl";
    public static final String WSDL_MEDIA_PROFILE = "http://www.onvif.org/ver10/media/wsdl";
    public static final String WSDL_MEDIA_URI = "http://www.onvif.org/ver10/media/wsdl";
    public static final String WSDL_PTZ = "http://www.onvif.org/ver20/ptz/wsdl";
    //ONVIF URL
    public static final String ONVIF_PATH_SERVICES = "/onvif/device_service";//Device Services URI
    public static final String ONVIF_PATH_DEVICE_INFORMATION = "/onvif/device_service";//Device Information URI
    public static final String ONVIF_PATH_PROFILES = "/onvif/device_service";//Device Profile URI
    public static final String ONVIF_PATH_STREAM_URI = "/onvif/device_service";//Device Media Stream URI
    //PTZ Move
    public static final int PTZ_MOVE_PLUS = (1);
    public static final int PTZ_MOVE_MINUS = (-1);
    public static final int PTZ_MOVE_IDLE = 0;
    //XML Requests
    public static final String REQUEST_SERVICE = "<GetServices xmlns=\"http://www.onvif.org/ver10/device/wsdl\">" +
                                                 "<IncludeCapability>false</IncludeCapability>" +
                                                 "</GetServices>";
    public static final String REQUEST_DEVICE_INFO = "<GetDeviceInformation xmlns=\"http://www.onvif.org/ver10/device/wsdl\">" +
                                                     "</GetDeviceInformation>";
    public static final String REQUEST_MEDIA_PROFILE = "<GetProfiles xmlns=\"http://www.onvif.org/ver10/media/wsdl\">" +
                                                       "</GetProfiles>";
    public static final String REQUEST_MEDIA_STREAM_URI = "<GetStreamUri xmlns=\"http://www.onvif.org/ver10/media/wsdl\">" +
                                                          "<StreamSetup>" +
                                                          "<Stream xmlns=\"http://www.onvif.org/ver10/schema\">RTP-Unicast</Stream>" +
                                                          "<Transport xmlns=\"http://www.onvif.org/ver10/schema\"><Protocol>RTSP</Protocol></Transport>" +
                                                          "</StreamSetup>" +
                                                          "<ProfileToken>%s</ProfileToken>" +
                                                          "</GetStreamUri>";
    public static final String REQUEST_PTZ = "<ContinuousMove xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">" +
                                             "<ProfileToken>%1$s</ProfileToken>" +
                                             "<Velocity>" +
                                             "<PanTilt x=\"%2$s\" y=\"%3$s\" xmlns=\"http://www.onvif.org/ver10/schema\"/>" +
                                             "<Zoom x=\"%4$s\" xmlns=\"http://www.onvif.org/ver10/schema\"/>" +
                                             "</Velocity>" +
                                             "</ContinuousMove>";
    public static final String REQUEST_PTZ_STOP = "<Stop xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">" +
                                                  "<ProfileToken>%s</ProfileToken>" +
                                                  "<PanTilt>" +
                                                  "true" +
                                                  "</PanTilt>" +
                                                  "<Zoom>" +
                                                  "true" +
                                                  "</Zoom>" +
                                                  "</Stop>";
    //XML Builder
    public static final String SOAP_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                                             "<soap:Envelope " +
                                             "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                                             "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                                             "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" >" +
                                             "<soap:Body>";
    public static final String DISCOVERY_SOAP_HEADER =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                                                        "<soap:Envelope " +
                                                        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                                                        "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                                                        "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" " +
                                                        "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" " +
                                                        "xmlns:tns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\">" +
                                                        "<soap:Header>" +
                                                        "<wsa:Action>http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</wsa:Action>" +
                                                        "<wsa:MessageID>urn:uuid:%s</wsa:MessageID>\n" +
                                                        "<wsa:To>urn:schemas-xmlsoap-org:ws:2005:04:discovery</wsa:To>\n" +
                                                        "</soap:Header>" +
                                                        "<soap:Body>";
    public static final String ENVELOPE_END = "</soap:Body></soap:Envelope>";
    public static final String DISCOVERY_SOAP_BODY = "<tns:Probe/>";

}
