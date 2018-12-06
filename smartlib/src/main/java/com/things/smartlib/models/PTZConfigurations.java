package com.things.smartlib.models;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         11/27/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class PTZConfigurations {
    private static final String TAG = PTZConfigurations.class.getSimpleName();

    private String token;
    private String name;
    private String nodetoken;
    private String DefaultAbsolutePantTiltPositionSpace;
    private String DefaultAbsoluteZoomPositionSpace;
    private String DefaultRelativePanTiltTranslationSpace;
    private String DefaultRelativeZoomTranslationSpace;
    private String DefaultContinuousPanTiltVelocitySpace;
    private String DefaultContinuousZoomVelocitySpace;
    private String DefaultPTZTimeout;

    private DefaultPTZSpeed defaultPTZSpeed;
    private PanTiltLimits panTiltLimits;
    private ZoomLimits zoomLimits;

    public PTZConfigurations() {
        this.defaultPTZSpeed = defaultPTZSpeed;
        this.panTiltLimits = panTiltLimits;
        this.zoomLimits = zoomLimits;
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


    public String getNodetoken() {
        return nodetoken;
    }

    public void setNodetoken(String nodetoken) {
        this.nodetoken = nodetoken;
    }

    public String getDefaultAbsolutePantTiltPositionSpace() {
        return DefaultAbsolutePantTiltPositionSpace;
    }

    public void setDefaultAbsolutePantTiltPositionSpace(String defaultAbsolutePantTiltPositionSpace) {
        DefaultAbsolutePantTiltPositionSpace = defaultAbsolutePantTiltPositionSpace;
    }

    public String getDefaultAbsoluteZoomPositionSpace() {
        return DefaultAbsoluteZoomPositionSpace;
    }

    public void setDefaultAbsoluteZoomPositionSpace(String defaultAbsoluteZoomPositionSpace) {
        DefaultAbsoluteZoomPositionSpace = defaultAbsoluteZoomPositionSpace;
    }

    public String getDefaultRelativePanTiltTranslationSpace() {
        return DefaultRelativePanTiltTranslationSpace;
    }

    public void setDefaultRelativePanTiltTranslationSpace(String defaultRelativePanTiltTranslationSpace) {
        DefaultRelativePanTiltTranslationSpace = defaultRelativePanTiltTranslationSpace;
    }

    public String getDefaultRelativeZoomTranslationSpace() {
        return DefaultRelativeZoomTranslationSpace;
    }

    public void setDefaultRelativeZoomTranslationSpace(String defaultRelativeZoomTranslationSpace) {
        DefaultRelativeZoomTranslationSpace = defaultRelativeZoomTranslationSpace;
    }

    public String getDefaultContinuousPanTiltVelocitySpace() {
        return DefaultContinuousPanTiltVelocitySpace;
    }

    public void setDefaultContinuousPanTiltVelocitySpace(String defaultContinuousPanTiltVelocitySpace) {
        DefaultContinuousPanTiltVelocitySpace = defaultContinuousPanTiltVelocitySpace;
    }

    public String getDefaultContinuousZoomVelocitySpace() {
        return DefaultContinuousZoomVelocitySpace;
    }

    public void setDefaultContinuousZoomVelocitySpace(String defaultContinuousZoomVelocitySpace) {
        DefaultContinuousZoomVelocitySpace = defaultContinuousZoomVelocitySpace;
    }

    public String getDefaultPTZTimeout() {
        return DefaultPTZTimeout;
    }

    public void setDefaultPTZTimeout(String defaultPTZTimeout) {
        DefaultPTZTimeout = defaultPTZTimeout;
    }

    public DefaultPTZSpeed getDefaultPTZSpeed() {
        return defaultPTZSpeed;
    }

    public void setDefaultPTZSpeed(DefaultPTZSpeed defaultPTZSpeed) {
        this.defaultPTZSpeed = defaultPTZSpeed;
    }

    public PanTiltLimits getPanTiltLimits() {
        return panTiltLimits;
    }

    public void setPanTiltLimits(PanTiltLimits panTiltLimits) {
        this.panTiltLimits = panTiltLimits;
    }

    public ZoomLimits getZoomLimits() {
        return zoomLimits;
    }

    public void setZoomLimits(ZoomLimits zoomLimits) {
        this.zoomLimits = zoomLimits;
    }

    public class DefaultPTZSpeed {
        private String pantiltspace;
        private String zoomspace;
        private String xpantilt;
        private String ypantilt;
        private String zzoom;

        public String getPantiltspace() {
            return pantiltspace;
        }

        public void setPantiltspace(String pantiltspace) {
            this.pantiltspace = pantiltspace;
        }

        public String getZoomspace() {
            return zoomspace;
        }

        public void setZoomspace(String zoomspace) {
            this.zoomspace = zoomspace;
        }

        public String getXpantilt() {
            return xpantilt;
        }

        public void setXpantilt(String xpantilt) {
            this.xpantilt = xpantilt;
        }

        public String getYpantilt() {
            return ypantilt;
        }

        public void setYpantilt(String ypantilt) {
            this.ypantilt = ypantilt;
        }

        public String getZzoom() {
            return zzoom;
        }

        public void setZzoom(String zzoom) {
            this.zzoom = zzoom;
        }

        @Override
        public String toString() {
            return "DefaultPTZSpeed{" +
                    "pantiltspace='" + pantiltspace + '\'' +
                    ", zoomspace='" + zoomspace + '\'' +
                    ", xpantilt='" + xpantilt + '\'' +
                    ", ypantilt='" + ypantilt + '\'' +
                    ", zzoom='" + zzoom + '\'' +
                    '}';
        }
    }

    public class PanTiltLimits {

        private String ptspaceuri;

        private XRange xRange;
        private YRange yRange;

        public String getPtspaceuri() {
            return ptspaceuri;
        }

        public void setPtspaceuri(String ptspaceuri) {
            this.ptspaceuri = ptspaceuri;
        }

        public XRange getxRange() {
            return xRange;
        }

        public void setxRange(XRange xRange) {
            this.xRange = xRange;
        }

        public YRange getyRange() {
            return yRange;
        }

        public void setyRange(YRange yRange) {
            this.yRange = yRange;
        }

        @Override
        public String toString() {
            return "PanTiltLimits{" +
                    "spaceuri='" + ptspaceuri +
                    ", xRange=" + xRange +
                    ", yRange=" + yRange +
                    '}';
        }
    }

    public class ZoomLimits {
        private String zspaceuri;

        private XRange xRange;

        public String getZspaceuri() {
            return zspaceuri;
        }

        public void setZspaceuri(String zspaceuri) {
            this.zspaceuri = zspaceuri;
        }

        public XRange getxRange() {
            return xRange;
        }

        public void setxRange(XRange xRange) {
            this.xRange = xRange;
        }

        @Override
        public String toString() {
            return "ZoomLimits{" +
                    "zspaceuri='" + zspaceuri + '\'' +
                    ", xRange=" + xRange +
                    '}';
        }
    }

    public class XRange {
        private String xmin;
        private String xmax;

        public String getXmin() {
            return xmin;
        }

        public void setXmin(String xmin) {
            this.xmin = xmin;
        }

        public String getXmax() {
            return xmax;
        }

        public void setXmax(String xmax) {
            this.xmax = xmax;
        }

        @Override
        public String toString() {
            return "XRange{" +
                    "xmin='" + xmin + '\'' +
                    ", xmax='" + xmax + '\'' +
                    '}';
        }
    }

    public class YRange {
        private String ymin;
        private String ymax;

        public String getYmin() {
            return ymin;
        }

        public void setYmin(String ymin) {
            this.ymin = ymin;
        }

        public String getYmax() {
            return ymax;
        }

        public void setYmax(String ymax) {
            this.ymax = ymax;
        }

        @Override
        public String toString() {
            return "YRange{" +
                    "ymin='" + ymin + '\'' +
                    ", ymax='" + ymax + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PTZConfigurations{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", nodetoken='" + nodetoken + '\'' +
                ", DefaultAbsolutePantTiltPositionSpace='" + DefaultAbsolutePantTiltPositionSpace + '\'' +
                ", DefaultAbsoluteZoomPositionSpace='" + DefaultAbsoluteZoomPositionSpace + '\'' +
                ", DefaultRelativePanTiltTranslationSpace='" + DefaultRelativePanTiltTranslationSpace + '\'' +
                ", DefaultRelativeZoomTranslationSpace='" + DefaultRelativeZoomTranslationSpace + '\'' +
                ", DefaultContinuousPanTiltVelocitySpace='" + DefaultContinuousPanTiltVelocitySpace + '\'' +
                ", DefaultContinuousZoomVelocitySpace='" + DefaultContinuousZoomVelocitySpace + '\'' +
                ", DefaultPTZTimeout='" + DefaultPTZTimeout + '\'' +
                ", defaultPTZSpeed=" + defaultPTZSpeed +
                ", panTiltLimits=" + panTiltLimits +
                ", zoomLimits=" + zoomLimits +
                '}';
    }
}
