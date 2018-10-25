package com.things.smartlib.models;

/**
 * The type Onvif device information.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifDeviceInformation {

    /**
     * The constant TAG.
     */
    public static final String TAG = OnvifDeviceInformation.class.getSimpleName();

    private String manufacturer;
    private String model;
    private String firmwareVersion;
    private String serialNumber;
    private String hardwareId;

    /**
     * Instantiates a new Onvif device information.
     */
    public OnvifDeviceInformation() {
    }

    /**
     * Gets manufacturer.
     *
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets manufacturer.
     *
     * @param manufacturer the manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets firmware version.
     *
     * @return the firmware version
     */
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * Sets firmware version.
     *
     * @param firmwareVersion the firmware version
     */
    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    /**
     * Gets serial number.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets serial number.
     *
     * @param serialNumber the serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Gets hardware id.
     *
     * @return the hardware id
     */
    public String getHardwareId() {
        return hardwareId;
    }

    /**
     * Sets hardware id.
     *
     * @param hardwareId the hardware id
     */
    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    @Override
    public String toString() {
        return "OnvifDeviceInformation{" +
                "manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", hardwareId='" + hardwareId + '\'' +
                '}';
    }
}
