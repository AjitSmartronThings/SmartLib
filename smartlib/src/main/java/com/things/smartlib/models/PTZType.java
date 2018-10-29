package com.things.smartlib.models;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/29/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public enum PTZType {
    LEFT_MOVE((-1),0,0),
    RIGHT_MOVE((1),0,0),
    UP_MOVE(0,(1),0),
    DOWN_MOVE(0,(-1),0),
    ZOOM_IN(0,0,(1)),
    ZOOM_OUT(0,0,(-1));

    public final int x,y,z;

    PTZType(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
