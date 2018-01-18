package com.dfire;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author xiaosuda
 * @date 2018/1/18
 */
public class SocketTestTwo {

    public static void main(String [] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            /**
             * 为每个请求创建一个新的线程来提供服务，实现更好的响应性
             */
            new Thread(() -> {
                handlerRequest(connection);
            }).start();
        }
    }

    public static void handlerRequest(Socket socket) {

    }
}
