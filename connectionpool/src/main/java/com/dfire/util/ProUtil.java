package com.dfire.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author xiaosuda
 * @date 2017/12/26
 */
public class ProUtil {


    static Properties properties = new Properties();

    static {
        InputStream resourceAsStream = ProUtil.class.getResourceAsStream("/application.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPro(String key) {
        return (String) properties.get(key);
    }

    public static void main(String [] args){
        System.out.println(getPro("username"));
    }
}
