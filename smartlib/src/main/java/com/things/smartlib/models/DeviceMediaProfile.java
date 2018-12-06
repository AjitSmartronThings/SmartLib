package com.things.smartlib.models;

import java.io.Serializable;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         11/19/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceMediaProfile {
    //token
    private String token;
    //name
    private String name;

    private VideoEncoderConfiguration videoEncode;
    private AudioEncoderConfiguration audioEncode;

    private PTZConfiguration ptzConfiguration;

    private String rtspUrl;

    public DeviceMediaProfile() {
        this.videoEncode = new VideoEncoderConfiguration();
        this.audioEncode = new AudioEncoderConfiguration();
        this.ptzConfiguration = new PTZConfiguration();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRtspUrl() {
        return rtspUrl;
    }

    public void setRtspUrl(String rtspUrl) {
        this.rtspUrl = rtspUrl;
    }

    public VideoEncoderConfiguration getVideoEncode() {
        return videoEncode;
    }

    public void setVideoEncode(VideoEncoderConfiguration videoEncode) {
        this.videoEncode = videoEncode;
    }

    public AudioEncoderConfiguration getAudioEncode() {
        return audioEncode;
    }

    public void setAudioEncode(AudioEncoderConfiguration audioEncode) {
        this.audioEncode = audioEncode;
    }

    public PTZConfiguration getPtzConfiguration() {
        return ptzConfiguration;
    }

    public void setPtzConfiguration(PTZConfiguration ptzConfiguration) {
        this.ptzConfiguration = ptzConfiguration;
    }

    @Override
    public String toString() {
        return "MediaProfile{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", videoEncode=" + videoEncode +
                ", audioEncode=" + audioEncode +
                ", rtspUrl='" + rtspUrl + '\'' +
                '}';
    }

    public class VideoEncoderConfiguration {
        private String token;

        private String encoding;

        private int width;
        private int height;

        private int frameRate;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getFrameRate() {
            return frameRate;
        }

        public void setFrameRate(int frameRate) {
            this.frameRate = frameRate;
        }

        @Override
        public String toString() {
            return "VideoEncoderConfiguration{" +
                    "token='" + token + '\'' +
                    ", encoding='" + encoding + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", frameRate=" + frameRate +
                    '}';
        }
    }


    public class AudioEncoderConfiguration {
        private String token;

        private String encoding;

        private int sampleRate;

        private int bitrate;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public int getSampleRate() {
            return sampleRate;
        }

        public void setSampleRate(int sampleRate) {
            this.sampleRate = sampleRate;
        }

        public int getBitrate() {
            return bitrate;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
        }

        @Override
        public String toString() {
            return "AudioEncoderConfiguration{" +
                    "token='" + token + '\'' +
                    ", encoding='" + encoding + '\'' +
                    ", sampleRate=" + sampleRate +
                    ", bitrate=" + bitrate +
                    '}';
        }
    }


    public class PTZConfiguration {

        private String token;

        private String nodeToken;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNodeToken() {
            return nodeToken;
        }

        public void setNodeToken(String nodeToken) {
            this.nodeToken = nodeToken;
        }

        @Override
        public String toString() {
            return "PTZConfiguration{" +
                    "token='" + token + '\'' +
                    ", nodeToken='" + nodeToken + '\'' +
                    '}';
        }
    }

}
