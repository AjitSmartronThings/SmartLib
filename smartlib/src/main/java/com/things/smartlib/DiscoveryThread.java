package com.things.smartlib;

import com.things.smartlib.listeners.DiscoveryCallback;
import com.things.smartlib.parsers.DiscoveryParser;
import com.things.smartlib.responses.OnvifResponse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * The type Discovery thread.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DiscoveryThread extends Thread {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = DiscoveryThread.class.getSimpleName();

    //Attributes
    private DatagramSocket server;
    private int timeout;
    private DiscoveryParser parser;
    private DiscoveryCallback callback;

    /**
     * Instantiates a new Discovery thread.
     *
     * @param server   the server
     * @param timeout  the timeout
     * @param mode     the mode
     * @param callback the callback
     */
//Constructors
    DiscoveryThread(DatagramSocket server, int timeout, DiscoveryMode mode, DiscoveryCallback callback) {
        super();
        this.server = server;
        this.timeout = timeout;
        this.callback = callback;
        parser = new DiscoveryParser(mode);
    }

    @Override
    public void run() {
        try {
            boolean started = false;
            DatagramPacket packet = new DatagramPacket(new byte[4096], 4096);
            server.setSoTimeout(timeout);
            long timerStarted = System.currentTimeMillis();
            while (System.currentTimeMillis() - timerStarted < timeout) {
                if (!started) {
                    callback.onDiscoveryStarted();
                    started = true;
                }

                server.receive(packet);
                String response = new String(packet.getData(), 0, packet.getLength());
                parser.setHostName(packet.getAddress().getHostName());
                //parser.setPort(String.valueOf(packet.getPort()));
                parser.setDeviceUrl();
                callback.onDevicesFound(parser.parse(new OnvifResponse(response)));
            }

        } catch (IOException ignored) {
            System.out.print(ignored.getStackTrace());
            callback.onDiscoveryTimeout();
        } finally {
            server.close();
            callback.onDiscoveryFinished();
        }
    }
}
