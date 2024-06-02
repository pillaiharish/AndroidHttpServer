package com.example.androidhttpserver;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.net.Inet4Address;
import java.net.SocketException;
import java.util.Enumeration;
import java.net.UnknownHostException;
import java.math.BigInteger;
import java.nio.ByteOrder;

public class NetworkUtils {

    public static String getHotspotIPAddress(Context context) {
        try {
            for (NetworkInterface intf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (intf.getName().equalsIgnoreCase("wlan0") || intf.getName().equalsIgnoreCase("ap0")) {
                    List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                    for (InetAddress addr : addrs) {
                        if (!addr.isLoopbackAddress()) {
                            String sAddr = addr.getHostAddress();
                            boolean isIPv4 = sAddr.indexOf(':') < 0;
                            if (isIPv4)
                                return sAddr;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    public static String getWifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endian if needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        try {
            return InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            return "Unable to get IP address";
        }
    }
    // this is working in android mobile
    public static String getHotspotIPAdd(Context context) {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                if (intf.getName().contains("wlan")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                            return inetAddress.getHostAddress(); // your IP address
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            return "Unable to get IP address";
        }
        return "No suitable IP address found";
    }

    public static String getHotspotIP(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        try {
            Method method = wifiManager.getClass().getDeclaredMethod("getDhcpInfo");
            DhcpInfo dhcpInfo = (DhcpInfo) method.invoke(wifiManager);
            int address = dhcpInfo.serverAddress;
            byte[] bytes = BigInteger.valueOf(address).toByteArray();
            InetAddress inetAddress = InetAddress.getByAddress(bytes);
            return inetAddress.getHostAddress();
        } catch (Exception ex) {
            return "Unable to get IP address";
        }
    }


}