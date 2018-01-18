package com.dfire;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author xiaosuda
 * @date 2018/1/18
 */
public class SocketTestOne {

    public static void main(String [] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            /**
             * 每次只处理一次请求
             */
            handlerRequest(connection);
        }
    }

    /**
     * 处理客户端请求  读取套接字  访问数据库 等等
     * @param socket
     */
    public static void handlerRequest(Socket socket) {
    }
}
