package com.things.smartlib;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import com.burgstaller.okhttp.AuthenticationCacheInterceptor;
import com.burgstaller.okhttp.CachingAuthenticatorDecorator;
import com.burgstaller.okhttp.digest.CachingAuthenticator;
import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.DigestAuthenticator;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifServices;
import com.things.smartlib.models.OnvifType;
import com.things.smartlib.parsers.GetDeviceInformationParser;
import com.things.smartlib.parsers.GetMediaProfilesParser;
import com.things.smartlib.parsers.GetMediaStreamParser;
import com.things.smartlib.parsers.GetServicesParser;
import com.things.smartlib.requests.GetDeviceInformationRequest;
import com.things.smartlib.requests.GetMediaProfilesRequest;
import com.things.smartlib.requests.GetMediaStreamRequest;
import com.things.smartlib.requests.GetServicesRequest;
import com.things.smartlib.requests.OnvifRequest;
import com.things.smartlib.requests.PTZRequest;
import com.things.smartlib.responses.OnvifResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.things.smartlib.models.OnvifType.GET_DEVICE_INFORMATION;
import static com.things.smartlib.models.OnvifType.GET_MEDIA_PROFILES;
import static com.things.smartlib.models.OnvifType.GET_STREAM_URI;

/**
 * The type Onvif executor.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifExecutor {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = OnvifExecutor.class.getSimpleName();

    //Attributes
    private OkHttpClient client;
    private MediaType reqBodyType;
    private RequestBody reqBody;

    private Credentials credentials;
    private OnvifResponseListener onvifResponseListener;

    //Constructors

    /**
     * Instantiates a new Onvif executor.
     *
     * @param onvifResponseListener the onvif response listener
     */
    OnvifExecutor(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        credentials = new Credentials("username", "password");
        DigestAuthenticator authenticator = new DigestAuthenticator(credentials);
        Map<String, CachingAuthenticator> authCache = new ConcurrentHashMap<>();

        client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS)
                .authenticator(new CachingAuthenticatorDecorator(authenticator, authCache))
                .addInterceptor(new AuthenticationCacheInterceptor(authCache))
                .build();

        reqBodyType = MediaType.parse("application/soap+xml; charset=utf-8;");
    }

    //Methods

    /**
     * Sends a request to the Onvif-compatible device.
     *
     * @param device  the device
     * @param request the request
     */
    void sendRequest(OnvifDevice device, OnvifRequest request) {
        credentials.setUserName(device.getUsername());
        credentials.setPassword(device.getPassword());
        reqBody = RequestBody.create(reqBodyType, OnvifXMLBuilder.getSoapHeader() + request.getXml() + OnvifXMLBuilder.getEnvelopeEnd());
        performXmlRequest(device, request, buildOnvifRequest(device, request));
    }

    /**
     * Clears up the resources.
     */
    void clear() {
        onvifResponseListener = null;
    }

    //Properties

    /**
     * Sets onvif response listener.
     *
     * @param onvifResponseListener the onvif response listener
     */
    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    private void performXmlRequest(OnvifDevice device, OnvifRequest request, Request xmlRequest) {
        if (xmlRequest == null)
            return;

        client.newCall(xmlRequest)
                .enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response xmlResponse) throws IOException {

                        OnvifResponse response = new OnvifResponse(request);
                        ResponseBody xmlBody = xmlResponse.body();

                        if (xmlResponse.code() == 200 && xmlBody != null) {
                            response.setSuccess(true);
                            response.setXml(xmlBody.string());
                            onvifResponseListener.onResponse(device,response);
                            parseResponse(device, response);
                            return;
                        }

                        String errorMessage = "";
                        if (xmlBody != null)
                            errorMessage = xmlBody.string();

                        onvifResponseListener.onError(device, xmlResponse.code(), errorMessage);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        onvifResponseListener.onError(device, -1, e.getMessage());
                    }

                });
    }

    private void parseResponse(OnvifDevice device, OnvifResponse response) {
        switch (response.getOnvifRequest().getType()) {
            case GET_SERVICES:
                OnvifServices path = new GetServicesParser().parse(response);
                device.setPath(path);
                ((GetServicesRequest) response.getOnvifRequest()).getListener().onServicesReceived(device, path);
                break;
            case GET_DEVICE_INFORMATION:
                ((GetDeviceInformationRequest) response.getOnvifRequest()).getListener().onDeviceInformationReceived(device,
                        new GetDeviceInformationParser().parse(response));
                break;
            case GET_MEDIA_PROFILES:
                ((GetMediaProfilesRequest) response.getOnvifRequest()).getListener().onMediaProfileReceived(device,
                        new GetMediaProfilesParser().parse(response));
                break;
            case GET_STREAM_URI:
                GetMediaStreamRequest streamRequest = (GetMediaStreamRequest) response.getOnvifRequest();
                streamRequest.getListener().onvifStreamUriReceived(device, streamRequest.getMediaProfile(),
                        new GetMediaStreamParser().parse(response));
                break;
            case PTZ:
                PTZRequest ptzRequest = (PTZRequest) response.getOnvifRequest();
                ptzRequest.getOnvifPTZListener().onPTZReceived(device,response.isSuccess());
                break;
            default:
                onvifResponseListener.onResponse(device, response);
                break;
        }
    }

    private Request buildOnvifRequest(OnvifDevice device, OnvifRequest request) {
        return new Request.Builder()
                .url(getUrlForRequest(device, request))
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .post(reqBody)
                .build();
    }

    private String getUrlForRequest(OnvifDevice device, OnvifRequest request) {
        return device.getHost() + getPathForRequest(device, request);
    }

    private String getPathForRequest(OnvifDevice device, OnvifRequest request) {
        switch (request.getType()) {
            case GET_SERVICES:
                return device.getPath().getServicepath();
            case GET_DEVICE_INFORMATION:
                return device.getPath().getDeviceinfomationpath();
            case GET_MEDIA_PROFILES:
                return device.getPath().getProfilespath();
            case GET_STREAM_URI:
                return device.getPath().getStreamURIpath();
        }

        return device.getPath().getServicepath();
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

