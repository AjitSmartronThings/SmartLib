package com.things.smartlib.models;

import static com.things.smartlib.TronXConstants.PTZ_MOVE_IDLE;
import static com.things.smartlib.TronXConstants.PTZ_MOVE_MINUS;
import static com.things.smartlib.TronXConstants.PTZ_MOVE_PLUS;

/**
 * The enum Ptz type.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/29/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public enum PTZType {
    /**
     * Left move ptz type.
     */
    LEFT_MOVE(PTZ_MOVE_MINUS,PTZ_MOVE_IDLE,PTZ_MOVE_IDLE),//-1,0,0
    /**
     * Right move ptz type.
     */
    RIGHT_MOVE(PTZ_MOVE_PLUS,PTZ_MOVE_IDLE,PTZ_MOVE_IDLE),//1,0,0
    /**
     * Up move ptz type.
     */
    UP_MOVE(PTZ_MOVE_IDLE,PTZ_MOVE_PLUS,PTZ_MOVE_IDLE),//0,1,0
    /**
     * Down move ptz type.
     */
    DOWN_MOVE(PTZ_MOVE_IDLE,PTZ_MOVE_MINUS,PTZ_MOVE_IDLE),//0,-1,0
    /**
     * Zoom in ptz type.
     */
    ZOOM_IN(PTZ_MOVE_IDLE,PTZ_MOVE_IDLE,PTZ_MOVE_PLUS),//0,0,1
    /**
     * Zoom out ptz type.
     */
    ZOOM_OUT(PTZ_MOVE_IDLE,PTZ_MOVE_IDLE,PTZ_MOVE_MINUS);//0,0,-1

    /**
     * The X.
     */
    public final int x, /**
     * Y ptz type.
     */
    y, /**
     * Z ptz type.
     */
    z;

    PTZType(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
