package com.things.smartlib.parsers;

import com.things.smartlib.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * The type Onvif parser.
 *
 * @param <T> the type parameter
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.parsers
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public abstract class OnvifParser<T> {

    /**
     * The constant TAG.
     */
    public static final String TAG = OnvifParser.class.getSimpleName();


    private XmlPullParserFactory xmlPullParserFactory;
    private XmlPullParser xmlPullParser;
    /**
     * The Event type.
     * e.g.	START_DOCUMENT , END_DOCUMENT , START_TAG , END_TAG
     */
    int eventType;

    /**
     * Instantiates a new Onvif parser.
     */
    public OnvifParser() {
        try {
            /**
             * Create a new instance of a PullParserFactory used to create XML pull parser.
             */
        xmlPullParserFactory = XmlPullParserFactory.newInstance();
            /**
             * Specifies that the parser produced by this factory will provide support for XML namespaces.
             */
        xmlPullParserFactory.setNamespaceAware(true);
            /**
             *  Creates a new instance of a XML Pull Parser using the currently configured factory parameters.
             */
        xmlPullParser=xmlPullParserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets xml pull parser factory.
     *
     * @return the xml pull parser factory
     */
    protected XmlPullParserFactory getXmlPullParserFactory() {
        return xmlPullParserFactory;
    }

    /**
     * Sets xml pull parser factory.
     *
     * @param xmlPullParserFactory the xml pull parser factory
     */
    protected void setXmlPullParserFactory(XmlPullParserFactory xmlPullParserFactory) {
        this.xmlPullParserFactory = xmlPullParserFactory;
    }

    /**
     * Gets xml pull parser.
     *
     * @return the xml pull parser
     */
    public XmlPullParser getXmlPullParser() {
        return xmlPullParser;
    }

    /**
     * Parse t.
     *
     * @param response the response
     * @return the t
     */
    public abstract T parse(OnvifResponse response);
}
