package com.things.smartcam;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      TCam
 * @package :      com.things.tcam
 * @date :         11/30/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class TronXCamera {
    private static final String TAG = TronXCamera.class.getSimpleName();

    private int id = -1;
    private String name = "";
    private String username = "";
    private String password = "";
    private String internalHost = "";
    private String internalHttp = "";
    private String internalRtsp = "";
    private String rtspurl = "";
    private String profile = "";

    public TronXCamera() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInternalHost() {
        return internalHost;
    }

    public void setInternalHost(String internalHost) {
        this.internalHost = internalHost;
    }

    public String getInternalHttp() {
        return internalHttp;
    }

    public void setInternalHttp(String internalHttp) {
        this.internalHttp = internalHttp;
    }

    public String getInternalRtsp() {
        return internalRtsp;
    }

    public void setInternalRtsp(String internalRtsp) {
        this.internalRtsp = internalRtsp;
    }

    public String getRtspurl() {
        return rtspurl;
    }

    public void setRtspurl(String rtspurl) {
        this.rtspurl = rtspurl;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TronXCamera that = (TronXCamera) o;

        if (id != that.id) return false;
        if (internalHttp != that.internalHttp) return false;
        if (internalRtsp != that.internalRtsp) return false;
        if (!name.equals(that.name)) return false;
        if (!username.equals(that.username)) return false;
        if (!password.equals(that.password)) return false;
        return internalHost.equals(that.internalHost);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + internalHost.hashCode();
        result = 31 * result + internalHttp.hashCode();
        result = 31 * result + internalRtsp.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TronXCamera{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", internalHost='" + internalHost + '\'' +
                ", internalHttp=" + internalHttp +
                ", internalRtsp=" + internalRtsp +
                '}';
    }
}
