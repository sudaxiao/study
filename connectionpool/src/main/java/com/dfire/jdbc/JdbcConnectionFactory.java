package com.dfire.jdbc;

import com.dfire.util.ProUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author xiaosuda
 * @date 2017/12/26
 */
public class JdbcConnectionFactory {

    private static final String     url             = ProUtil.getPro("url");
    private static final String     userName        = ProUtil.getPro("username");
    private static final String     password        = ProUtil.getPro("password");
    private static final String     driverClassName = ProUtil.getPro("driver-class-name");
    private static       Connection connection      = null;

    private synchronized static void initConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(driverClassName);
                connection = DriverManager.getConnection(url, userName, password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getStaticConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = null;
                initConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getNewConnection() {
        Connection connection = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
