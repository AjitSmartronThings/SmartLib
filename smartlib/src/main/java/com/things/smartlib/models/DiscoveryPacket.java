package com.things.smartlib.models;

import com.things.smartlib.DiscoveryMode;
import com.things.smartlib.OnvifXMLBuilder;

import java.util.Locale;

/**
 * The type Discovery packet.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DiscoveryPacket extends OnvifPacket {
    /**
     * The constant TAG.
     */
    public static final String TAG = DiscoveryPacket.class.getSimpleName();
    private static final String LINE_END = "\r\n";
    private static final String DEFAULT_QUERY = "M-SEARCH * HTTP/1.1" + LINE_END +
            "HOST: 239.255.255.250:1900" + LINE_END +
            "MAN: \"ssdp:discover\"" + LINE_END +
            "MX: 1" + LINE_END +
            //"ST: urn:schemas-upnp-org:service:AVTransport:1" + LINE_END + // Use for Sonos
            //"ST: urn:schemas-upnp-org:device:InternetGatewayDevice:1" + LINE_END + // Use for Routes
            "ST: ssdp:all" + LINE_END + // Use this for all UPnP Devices
            LINE_END;

    //Attributes
    private final String uuid;
    private final DiscoveryMode mode;

    /**
     * Instantiates a new Discovery packet.
     *
     * @param uuid the uuid
     */
//Constructors
    public DiscoveryPacket(String uuid) {
        this(uuid, DiscoveryMode.ONVIF);
    }

    /**
     * Instantiates a new Discovery packet.
     *
     * @param uuid the uuid
     * @param mode the mode
     */
    public DiscoveryPacket(String uuid, DiscoveryMode mode) {
        this.uuid = uuid;
        this.mode = mode;
    }

    @Override
    public byte[] getData() {
        String data = "";
        if (mode.equals(DiscoveryMode.ONVIF)) {
            StringBuilder builder = new StringBuilder();
            String header = String.format(Locale.getDefault(), OnvifXMLBuilder.getDiscoverySoapHeader(), uuid);
            builder.append(header);
            builder.append(OnvifXMLBuilder.getDiscoverySoapBody());
            builder.append(OnvifXMLBuilder.getEnvelopeEnd());
            data = builder.toString();
        } else if (mode.equals(DiscoveryMode.UPNP)) {
            data = DEFAULT_QUERY;
        }

        return data.getBytes();
    }
}
