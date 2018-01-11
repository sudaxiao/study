package com.dfire.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.dfire.util.ProUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaosuda
 * @date 2017/12/26
 */
public class ConnectionPool {

    private static String DRIVER_CLASS_NAME = ProUtil.getPro("druid-class-name");
    private static String USERNAME          = ProUtil.getPro("username");
    private static String PASSWORD          = ProUtil.getPro("password");
    private static String URL               = ProUtil.getPro("url");
    private static Map<String, DruidPooledConnection> cache = new ConcurrentHashMap<>();
    private static DruidDataSource dataSource1;
    private static DruidPooledConnection createDruidPooledConnection() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setUrl(URL);
        dataSource.setInitialSize(30);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(50);
        dataSource.setTimeBetweenConnectErrorMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(false);
        dataSource.setConnectionInitSqls(Arrays.asList("set names utf8mb4"));
        dataSource1 = dataSource;
        return dataSource.getConnection();
    }

    public static DruidPooledConnection getConnection() {
        DruidPooledConnection pooledConnection = cache.get("xiaosuda");
        try {
            if (pooledConnection == null || pooledConnection.isClosed()) {
                pooledConnection = createDruidPooledConnection();
                cache.put("xiaosuda", pooledConnection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pooledConnection;

    }

    public static DruidPooledConnection getDruidConnection() {
        if (dataSource1 == null) {
            try {
                createDruidPooledConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            return dataSource1.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeDruidConnection(Connection connection) {

        if (connection instanceof DruidPooledConnection) {
            DruidPooledConnection connection1 = (DruidPooledConnection) connection;
            try {
                connection1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection1.abandond();
        }


    }

}
