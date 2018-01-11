package com.dfire.date;

import java.time.*;

/**
 * Created by xiaosuda on 2018/1/3.
 */
public class DateTime {
    public static void main(String [] args){
        LocalDate now = LocalDate.now();
        System.out.println(now);

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);


        LocalDate before = LocalDate.of(2017,10,21);

        Period between = Period.between(before, now);
        System.out.println(between.getYears() + " " + between.getMonths() + " " + between.getDays());



        LocalDateTime now1 = LocalDateTime.now();
        LocalDateTime before1 = LocalDateTime.of(2017,12,3,0,0,0);
        Duration between1 = Duration.between(before1, now1);
        System.out.println(between1.getSeconds() / 60 / 24 / 60);
    }
}
