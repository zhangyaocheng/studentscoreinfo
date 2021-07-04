package com.example.studentscoreinfo.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Integer工具类
 */
@Slf4j
public class IntegerUtil {

    public static boolean isBlink(Integer data){
        if (data ==null || data <=0){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        Integer data = 1;
        boolean result = isBlink(data);
        log.info("{} isBlink? {}", data, result);
    }

}
