package com.example.studentscoreinfo.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 处理字符串相关内容
 */
@Slf4j
public class StringUtil {

    public static boolean isBlink(String str){
        if (str == null || str.isEmpty()){
            return true;
        }
        return false;
    }

    public static void main(String[] args){

        String str = "89";
        boolean result = isBlink(str);
        log.info("{} 是否为null或者 ''? {}", str, result);
    }

}
