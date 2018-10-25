package com.things.smartlib.listeners;

import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifMediaProfile;

import java.util.List;

/**
 * The interface Onvif media profile listener.
 */
public interface OnvifMediaProfileListener {
    /**
     * On media profile received.
     *
     * @param onvifDevice   the onvif devcie
     * @param mediaProfiles the media profiles
     */
    void onMediaProfileReceived(OnvifDevice onvifDevice, List<OnvifMediaProfile> mediaProfiles);
}
