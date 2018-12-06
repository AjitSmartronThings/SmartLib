package com.things.smartlib;

import android.content.Context;

import com.things.smartlib.models.Device;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;


public class FindDevicesThread extends Thread {

    private byte[] sendData;
    private boolean readResult = false, receiveTag = true;
    //Callback
    private FindDevicesListener listener;

    public FindDevicesThread(Context context, FindDevicesListener listener) {
        this.listener = listener;
        InputStream fis = null;
        try {
            //从assets读取文件
            fis = context.getAssets().open("probe.xml");
            sendData = new byte[fis.available()];
            readResult = fis.read(sendData) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        super.run();
        DatagramSocket udpSocket = null;
        DatagramPacket receivePacket;
        DatagramPacket sendPacket;
        //设备列表集合
        ArrayList<Device> devices = new ArrayList<>();
        ArrayList uuidArrayList = new ArrayList();
        byte[] by = new byte[1024 * 3];
        if (readResult) {
            try {
                //端口号
                int BROADCAST_PORT = 3702;
                //初始化
                udpSocket = new DatagramSocket(BROADCAST_PORT);
                udpSocket.setSoTimeout(5 * 1000);
                udpSocket.setBroadcast(true);
                //DatagramPacket
                sendPacket = new DatagramPacket(sendData, sendData.length);
                sendPacket.setAddress(InetAddress.getByName("239.255.255.250"));
                sendPacket.setPort(BROADCAST_PORT);
                //发送
                udpSocket.send(sendPacket);
                //接受数据
                receivePacket = new DatagramPacket(by, by.length);
                while (receiveTag) {
                    udpSocket.receive(receivePacket);
                    String str = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    if (uuidArrayList.contains(XmlDecodeUtil.getDeviceInfo(str).getHost())) {
                    } else {
                        uuidArrayList.add(XmlDecodeUtil.getDeviceInfo(str).getHost());
                        devices.add(XmlDecodeUtil.getDeviceInfo(str));
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (udpSocket != null) {
                    udpSocket.close();
                }
            }
        }
        receiveTag = false;
        //回调结果
        if (listener != null) {
            listener.searchResult(devices);
        }
    }

    /**
     * Author ： BlackHao
     * Time : 2018/1/9 11:13
     * Description : 搜索设备回调
     */
    public interface FindDevicesListener {
        void searchResult(ArrayList<Device> devices);
    }
}
