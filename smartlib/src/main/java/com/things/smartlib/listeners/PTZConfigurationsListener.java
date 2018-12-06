package com.things.smartlib.listeners;

import android.widget.LinearLayout;

import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.PTZConfigurations;

import java.util.List;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.listeners
 * @date :         11/27/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public interface PTZConfigurationsListener {
    void onPTZConfigurationsReceived(OnvifDevice onvifDevice, List<PTZConfigurations> ptzConfigurations);
}
