package com.things.smartlib;

import com.things.smartlib.listeners.DiscoveryCallback;
import com.things.smartlib.listeners.DiscoveryListener;
import com.things.smartlib.models.Device;
import com.things.smartlib.models.DiscoveryPacket;
import com.things.smartlib.models.OnvifPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.things.smartlib.TronXConstants.DISCOVERY_TIMEOUT;
import static com.things.smartlib.TronXConstants.MULTICAST_ADDRESS_IPV4;
import static com.things.smartlib.TronXConstants.MULTICAST_ADDRESS_IPV6;

/**
 * The type Onvif discovery.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifDiscovery {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = OnvifDiscovery.class.getSimpleName();

    private static final Random random = new SecureRandom();

    //Attributes
    private int discoveryTimeout = DISCOVERY_TIMEOUT;
    private DiscoveryMode mode;
    private DiscoveryListener discoveryListener;

    /**
     * Instantiates a new Onvif discovery.
     */
//Constructors
    OnvifDiscovery() {
        this(DiscoveryMode.UPNP);
    }

    /**
     * Instantiates a new Onvif discovery.
     *
     * @param mode the mode
     */
    public OnvifDiscovery(DiscoveryMode mode) {
        this.mode = mode;
    }

    //Methods

    /**
     * Gets discovery timeout.
     *
     * @return the discovery timeout
     */
    int getDiscoveryTimeout() {
        return discoveryTimeout;
    }

    /**
     * Sets the timeout in milliseconds to discover devices.
     * Defaults to 10 seconds
     *
     * @param timeoutMs the timeout in milliseconds
     */
    void setDiscoveryTimeout(int timeoutMs) {
        discoveryTimeout = timeoutMs;
    }

    /**
     * Gets discovery mode.
     *
     * @return the discovery mode
     */
    DiscoveryMode getDiscoveryMode() {
        return mode;
    }

    /**
     * Sets discovery mode.
     *
     * @param mode the mode
     */
    void setDiscoveryMode(DiscoveryMode mode) {
        this.mode = mode;
    }

    /**
     * Sets discovery listener.
     *
     * @param discoveryListener the discovery listener
     */
    public void setDiscoveryListener(DiscoveryListener discoveryListener) {
        this.discoveryListener = discoveryListener;
    }

    /**
     * Broadcasts a SOAP-over-UDP package to all network interfaces
     * Discoveries are sent over multicast, replies are sent over unicast (both in UDP)
     * It means that the device will receive the discovery query, but it will not be able to answer back to the discovery tool if the peers are working on different subnets.
     *
     * @param mode              the mode
     * @param discoveryListener the discovery listener
     */
    void probe(DiscoveryMode mode, DiscoveryListener discoveryListener) {
        //Sets the mode and discovery callback
        this.mode = mode;
        this.discoveryListener = discoveryListener;

        //Get all interface addresses to send the UDP package on.
        List<InetAddress> addresses = getInterfaceAddresses();

        //Broadcast the message over all the network interfaces
        broadcast(addresses);
    }

    //Properties

    private void broadcast(List<InetAddress> addresses) {
        //Our list will be accessed by multiple threads, hence ConcurrentSkipListSet
        Collection<Device> devices = new ConcurrentSkipListSet<>();

        //Create a new cached thread pool and a monitor service
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService monitor = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(addresses.size());
        List<Runnable> runnables = new ArrayList<>();

        //Add runnables to the list to be executed in order
        for (InetAddress address : addresses) {
            runnables.add(() -> {
                try {
                    OnvifPacket packet = createDiscoveryPacket();
                    byte[] data = packet.getData();

                    int port = random.nextInt( 20000) + 40000;

                    DatagramSocket client = new DatagramSocket(port, address);
                    client.setBroadcast(true);

                    //Start a new thread to listen for incoming UDP packages
                    new DiscoveryThread(client, discoveryTimeout, mode, new DiscoveryCallback() {

                        @Override
                        public void onDiscoveryStarted() {
                            try {
                                if (address instanceof Inet4Address) {
                                    client.send(new DatagramPacket(data, data.length, InetAddress.getByName(MULTICAST_ADDRESS_IPV4), mode.port));
                                } else {
                                    client.send(new DatagramPacket(data, data.length, InetAddress.getByName(MULTICAST_ADDRESS_IPV6), mode.port));
                                }
                            } catch (IOException ignored) {
                            }
                        }

                        @Override
                        public void onDevicesFound(List<Device> onvifDevices) {
                            devices.addAll(onvifDevices);
                            discoveryListener.onDevicesFound(onvifDevices);
                        }

                        @Override
                        public void onDiscoveryFinished() {
                            latch.countDown();
                        }

                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }

        //Notify that we started our discovery
        notifyDiscoveryStarted();

        //Execute a new thread for every probe that should be sent.
        monitor.submit(() -> {
            for (Runnable runnable : runnables) {
                executorService.submit(runnable);
            }

            //Stop accepting new tasks and shuts down threads as they finish
            try {
                executorService.shutdown();

                latch.await(discoveryTimeout, TimeUnit.MILLISECONDS);
                boolean cleanShutdown = executorService.awaitTermination(discoveryTimeout,
                        TimeUnit.MILLISECONDS);

                if (!cleanShutdown) {
                    executorService.shutdownNow();
                }

                notifyDevicesFound(new ArrayList<>(devices));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        monitor.shutdown();

    }

    private OnvifPacket createDiscoveryPacket() {
        String uuid = UUID.randomUUID().toString();
        return new DiscoveryPacket(uuid, mode);
    }

    /**
     * Gets interface addresses.
     *
     * @return the interface addresses
     */
    List<InetAddress> getInterfaceAddresses() {
        List<InetAddress> addresses = new ArrayList<>();
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    addresses.add(address.getAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    /**
     * Gets broadcast addresses.
     *
     * @return the broadcast addresses
     */
    List<InetAddress> getBroadcastAddresses() {
        List<InetAddress> addresses = new ArrayList<>();
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = address.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }

                    addresses.add(broadcast);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    /**
     * Gets local ip address.
     *
     * @return the local ip address
     */
    String getLocalIpAddress() {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ignored) {

        }
        return null;
    }

    private void notifyDiscoveryStarted() {
        if (discoveryListener != null)
            discoveryListener.onDiscoveryStarted();
    }

    private void notifyDevicesFound(List<Device> devices) {
        if (discoveryListener != null)
            discoveryListener.onDevicesFound(devices);
    }

}

