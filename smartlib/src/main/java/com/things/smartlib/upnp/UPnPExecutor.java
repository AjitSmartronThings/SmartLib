package com.things.smartlib.upnp;

import com.things.smartlib.models.UPnPDevice;
import com.things.smartlib.parsers.UPnPParser;
import com.things.smartlib.responses.OnvifResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.upnp
 * @date :         11/1/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class UPnPExecutor {
    //Constants
    public static final String TAG = UPnPExecutor.class.getSimpleName();

    //Attributes
    private OkHttpClient client;
    private UPnPResponseListener responseListener;

    //Constructors

    UPnPExecutor(UPnPResponseListener responseListener) {
        this.responseListener = responseListener;

        client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS)
                .build();
    }

    //Methods

    /**
     * Sends a request to a UPnP device.
     */
    public void sendRequest(UPnPDevice device) {
        performXmlRequest(device, buildUPnPRequest(device));
    }

    /**
     * Sends a request to a UPnP device.
     */
    void getDeviceInformation(UPnPDevice device, UPnPDeviceInformationListener listener) {
        Request request = buildUPnPRequest(device);
        client.newCall(request)
                .enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response xmlResponse) throws IOException {
                        ResponseBody xmlBody = xmlResponse.body();

                        if (xmlResponse.code() == 200 && xmlBody != null) {
                            UPnPDeviceInformation information = parseDeviceInformation(device, xmlBody.string());
                            device.setDeviceInformation(information);
                            listener.onDeviceInformationReceived(device, information);
                            return;
                        }

                        String errorMessage = "";
                        if (xmlBody != null)
                            errorMessage = xmlBody.string();

                        listener.onError(device, xmlResponse.code(), errorMessage);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onError(device, -1, e.getMessage());
                    }

                });
    }

    /**
     * Clears up the resources.
     */
    public void clear() {
        responseListener = null;
    }

    //Properties

    public void setResponseListener(UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    private void performXmlRequest(UPnPDevice device, Request xmlRequest) {
        if (xmlRequest == null)
            return;

        client.newCall(xmlRequest)
                .enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response xmlResponse) throws IOException {
                        ResponseBody xmlBody = xmlResponse.body();

                        if (xmlResponse.code() == 200 && xmlBody != null) {
                            parseResponse(device, xmlBody.string());
                            return;
                        }

                        String errorMessage = "";
                        if (xmlBody != null)
                            errorMessage = xmlBody.string();

                        responseListener.onError(device, xmlResponse.code(), errorMessage);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        responseListener.onError(device, -1, e.getMessage());
                    }

                });
    }

    private UPnPDeviceInformation parseDeviceInformation(UPnPDevice device, String xmlBody) {
        return new UPnPParser().parse(new OnvifResponse(xmlBody));
    }

    private void parseResponse(UPnPDevice device, String xmlBody) {
        UPnPParser parser = new UPnPParser();
        parser.parse(new OnvifResponse(xmlBody));
    }

    private Request buildUPnPRequest(UPnPDevice device) {
        return new Request.Builder()
                .url(device.getLocation())
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .get()
                .build();
    }

    private String bodyToString(Request request) {

        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            if (copy.body() != null)
                copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
