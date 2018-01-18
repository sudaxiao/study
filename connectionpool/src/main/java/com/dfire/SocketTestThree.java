package com.dfire;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author xiaosuda
 * @date 2018/1/18
 */
public class SocketTestThree {

    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String [] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            /**
             * 使用可以同时容纳100个客户端连接的线程池来处理请求
             */
            exec.execute(() -> {
                handlerRequest(connection);
            });
        }
    }

    public static void handlerRequest(Socket socket) {

    }
}
