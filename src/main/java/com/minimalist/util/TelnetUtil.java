package com.minimalist.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TelnetUtil {

    public static boolean telnet(String hostname, int port, int timeout) {
        Socket socket = new Socket();
        boolean isConnected = false;
        try {
            socket.connect(new InetSocketAddress(hostname, port), timeout); // 建立连接
            isConnected = socket.isConnected(); // 通过现有方法查看连通状态
        } catch (Exception e) {
        } finally {
            try {
                socket.close();   // 关闭连接
            } catch (Exception e) {
            }
        }
        return isConnected;
    }
}