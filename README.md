# SmartLib - A Onvif Library
---
**SmartLib** is useful for discovery IP cameras within networks which supports ONVIF and UPNP protocols.We can also add camera which supports these protocols.

## Features

  - **ONVIF & UPnP discovery**
  - ONVIF device management (Services, device information, media profiles, raw media stream uri)
  - UPnP device information
  - Easily extendable with your custom requests

## Discovery
---
The OnvifDiscovery class uses the **Web Services Dynamic Discovery (WS-Discovery)**. This is a technical specification that defines a multicast discovery protocol to locate services on a local network. It operates over TCP and UDP port ```3702``` and uses IP multicast address ```239.255.255.250```. As the name suggests, the actual communication between nodes is done using web services standards, notably **SOAP-over-UDP**.

With WS-Discovery, the discovery tool puts SSDP queries on the network from its unicast address to ```239.255.255.250``` multicast address, sending them to the well-known UDP port 3702. The device receives the query, and answers to the discovery tool's unicast IP address from its unicast IP address. The reply contains information about the Web Services (WS) available on the device.

**UPnP** works in a very similar way, but on a different UDP port (```1900```).
Compared to the WS-Discovery, the UPnP is intended for a general use (data sharing, communication, entertainment).

```java
DiscoveryManager manager = new DiscoveryManager();
manager.setDiscoveryTimeout(10000);
manager.discover(new DiscoveryListener() {
    @Override
    public void onDiscoveryStarted() {
        System.out.println("Discovery started");
    }

    @Override
    public void onDevicesFound(List<Device> devices) {
        for (Device device : devices)
            System.out.println("Devices found: " + device.getHostName());
    }
});
```

## ONVIF
---

With the ```OnvifManager``` class it is possible to send requests to an ONVIF-supported device. All requests are sent asynchronously and you can use the ```OnvifResponseListener``` for errors and custom response handling. It is possible to create your own ```OnvifDevice``` or retrieve a list from the ```discover``` method in the ```DiscoveryManager```

```java
onvifManager = new OnvifManager();
onvifManager.setOnvifResponseListener(this);
OnvifDevice device = new OnvifDevice("192.168.0.4:36000", "admin", "admin");
```

### Services
Returns information about services on the device.

```java
onvifManager.getServices(device, new OnvifServicesListener() {
    @Override
    public void onServicesReceived(@Nonnull OnvifDevice onvifDevice, OnvifServices services) {
        
    }
});
```

### Device information
Returns basic device information from the device. This includes the manufacturer, serial number, hardwareId, ...

```java
onvifManager.getDeviceInformation(device, new OnvifDeviceInformationListener() {
    @Override
    public void onDeviceInformationReceived(@Nonnull OnvifDevice device, 
                                            @Nonnull OnvifDeviceInformation deviceInformation) {
        
    }
});
```

### Media Profiles
Returns pre-configured or dynamically configured profiles. This command lists all configured profiles in a device. The client does not need to know the media profile in order to use the command.

```java
onvifManager.getMediaProfiles(device, new OnvifMediaProfilesListener() {
    @Override
    public void onMediaProfilesReceived(@Nonnull OnvifDevice device, 
                                        @Nonnull List<OnvifMediaProfile> mediaProfiles) {
        
    }
});
```

### Media Stream URI
Returns a raw media stream URI that remains valid indefinitely even if the profile is changed.

```java
onvifManager.getMediaStreamURI(device, mediaProfiles.get(0), new OnvifMediaStreamURIListener() {
    @Override
    public void onMediaStreamURIReceived(@Nonnull OnvifDevice device, 
                                        @Nonnull OnvifMediaProfile profile, @Nonnull String uri) {
        
    }
});
```

### PTZ Request
Send request for Pan,Tilt and Zoom.This provides rotating IP Camera to its **Left,Right,Up** and **Down**.also
provides support for **ZoomIn** and **ZoomOut**.You have to provide PTZ Type like **LEFT_MOVE,RIGHT_MOVE,UP_MOVE,DOWN_MOVE**
for rotating respectively.also **ZOOM_IN** and **ZOOM_OUT** for zooming.

```java
onvifManager.sendPTZRequest(device, mediaProfile, PTZType.LEFT_MOVE, new OnvifPTZListener() {
                                           @Override
                                           public void onPTZReceived((@Nonnull OnvifDevice onvifDevice, boolean status) {
                                             //return PTZ status
    
    }
});
```

### STOP PTZ
You can use stopPTZRequest for stopping rotation and zooming.

```java
//for Stopping Pan,Tilt and Zoom
onvifManager.stopPTZRequest(device,mediaProfile);
```

## Custom requests
---

It is possible to implement your custom ONVIF request by creating a new class and implementing the ```OnvifRequest``` interface and overriding the ```getXml()``` and ```getType()``` methods.

```java
public class PTZRequest implements OnvifRequest {
    @Override
    public String getXml() {
        return "<GetServices xmlns=\"http://www.onvif.org/ver10/device/wsdl\">" +
                "<IncludeCapability>false</IncludeCapability>" +
                "</GetServices>";
    }
    @Override
    public OnvifType getType() {
        return OnvifType.CUSTOM;
    }
}
```

and send it to the appropriate ```OnvifDevice```:

```java
onvifManager.sendOnvifRequest(device, new PTZRequest());
```

Use the ```OnvifResponseListener``` to receive responses from your custom requests.

Download
--------

Download the latest JAR or grab via Maven:
```xml
<dependency>
	<groupId>com.github.AjitSmartronThings</groupId>
	<artifactId>SmartLib</artifactId>
	<version>v1.0.0</version>
</dependency>
```
or Gradle:
```groovy
implementation 'com.github.AjitSmartronThings:SmartLib:v1.0.0'
```

License
=======

    Copyright 2018 TThings - A Smartron Company.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    


