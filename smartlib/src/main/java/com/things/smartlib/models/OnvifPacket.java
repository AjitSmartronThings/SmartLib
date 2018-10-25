package com.things.smartlib.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Onvif packet.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifPacket {
    /**
     * The constant TAG.
     */
    public static final String TAG = OnvifPacket.class.getSimpleName();

    //Attributes
    private String name;
    private String timestamp;
    private byte[] data;

    /**
     * Instantiates a new Onvif packet.
     */
    public OnvifPacket() {
        this("", new byte[0]);
    }

    /**
     * Instantiates a new Onvif packet.
     *
     * @param name the name
     */
    public OnvifPacket(String name) {
        this(name, new byte[0]);
    }

    /**
     * Instantiates a new Onvif packet.
     *
     * @param name the name
     * @param data the data
     */
    public OnvifPacket(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get data byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Ascii to bytes byte [ ].
     *
     * @param ascii the ascii
     * @return the byte [ ]
     */
    public static byte[] asciiToBytes(String ascii) {

        int val1, val2, val3;
        char c1, c2;

        List<Byte> bytes = new ArrayList<>();

        ascii = ascii.replace("\\r", "\\0d");
        ascii = ascii.replace("\\n", "\\0a");

        for (int i = 0; i < ascii.length(); i++) {
            val1 = iAt(ascii, i);
            if (val1 >= 0x20 && val1 <= 0x7E) {

                if (val1 == (((int) '\\') & 0xff)) {
                    val2 = iAt(ascii, i + 1);
                    val3 = iAt(ascii, i + 2);
                    if (val2 > -1 && val3 > -1) {
                        c1 = ascii.charAt(i + 1);
                        c2 = ascii.charAt(i + 2);

                        try {
                            val2 = Integer.parseInt((c1 + "") + (c2 + ""), 16) & 0xff;
                            val3 = 0;

                            bytes.add((byte) (val2 + val3));

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        i += 2;
                    }

                } else {
                    val1 = iAt(ascii, i);
                    bytes.add((byte) (val1));

                }
            }
        }

        return toByteArray(bytes);
    }

    private static int iAt(String s, int index) {
        if (index < s.length()) {
            return (((int) s.charAt(index)) & 0xff);
        } else {
            return -1;
        }
    }

    private static byte[] toByteArray(List<Byte> list) {
        byte[] ret = new byte[list.size()];
        for (int i = 0; i < ret.length; i++)
            ret[i] = list.get(i);
        return ret;
    }

    /**
     * To ascii string.
     *
     * @param data the data
     * @return the string
     */
    public String toAscii(byte[] data) {
        StringBuilder returnString = new StringBuilder();
        for (int item : data) {
            if (item == 0x0A) {
                returnString.append("\\n");

            } else if (item == 0x0D) {
                returnString.append("\\r");

            } else if (item >= 0x20 && item <= 0x7E) {
                returnString.append((char) item);
            } else {
                String hex = Integer.toHexString(item & 0xff);
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                returnString.append("\\").append(hex);
            }
        }

        return returnString.toString();
    }
}
