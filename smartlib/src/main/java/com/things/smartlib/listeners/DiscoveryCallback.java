package com.things.smartlib.listeners;

import com.things.smartlib.models.Device;

import java.util.List;

/**
 * The interface Discovery callback.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.listeners
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public interface DiscoveryCallback {
    /**
     * On discovery started.
     */
    void onDiscoveryStarted();

    /**
     * On devices found.
     *
     * @param devices the devices
     */
    void onDevicesFound(List<Device> devices);

    /**
     * On discovery finished.
     */
    void onDiscoveryFinished();
}
