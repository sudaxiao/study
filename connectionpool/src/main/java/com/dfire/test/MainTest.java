package com.dfire.test;

import com.dfire.jdbc.ConnectionPool;
import com.dfire.jdbc.JdbcConnectionFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

/**
 *
 * @author xiaosuda
 * @date 2017/12/26
 */
public class MainTest {

    private static  volatile Integer x = 0;

    public static void main(String [] args){
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("staticConnection").build();
        ThreadPoolExecutor singleThreadPool = new ThreadPoolExecutor(threadNum, 30, 40L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.allowCoreThreadTimeOut(true);
        for ( int i = 0; i < threadNum; i++) {
            String threadName = "thread -" + i;
            singleThreadPool.execute(()->{
            //    countDownLatch.countDown();
           //     System.out.println(countDownLatch.getCount());
                try {
                 //   countDownLatch.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Thread.currentThread().setName(threadName);
         //       System.out.println(threadName + "开始执行");
                long start = System.currentTimeMillis();
                for (int j = 0; j < 500; j++) {
                  //   staticConnection(); //38
                   // newConnection();  //  22
                    druidConnection(); //  19
                }
                long end = System.currentTimeMillis();
                System.out.println( Thread.currentThread().getName()  +" 耗时" + (start - end));
            });
        }
        singleThreadPool.shutdown();

    }

    private static void staticConnection() {
        Connection staticConnection = JdbcConnectionFactory.getStaticConnection();
        doQuery(staticConnection, 0);
    }

    private static void newConnection() {
        Connection newConnection = JdbcConnectionFactory.getNewConnection();
        doQuery(newConnection, 1);
    }

    private static void druidConnection() {
      //  doQuery(ConnectionPool.getConnection(), 0);
        doQuery(ConnectionPool.getDruidConnection(), 2);

    }

    private static void doQuery(Connection connection, Integer type) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select count(*) FROM zeus_action_history");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (type == 0) {
                closeResource(preparedStatement, resultSet, null);
            } else if (type == 1) {
                closeResource(preparedStatement, resultSet, connection);
            } else if (type == 2) {
                closeResource(preparedStatement, resultSet, null);
                ConnectionPool.closeDruidConnection(connection);
            }
        }
    }

    private static void closeResource(PreparedStatement preparedStatement, ResultSet resultSet, Connection connection) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }

            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            preparedStatement = null;
            resultSet = null;
            connection = null;
        }
    }
}
