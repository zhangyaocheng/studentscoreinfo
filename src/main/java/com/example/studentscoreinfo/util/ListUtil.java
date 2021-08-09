package com.example.studentscoreinfo.util;

import java.util.List;

/**
 * 列表方法工具类
 */
public class ListUtil {

    /**
     * 对于字符串存储的数据，计算其平均分和百分比
     * @param list
     * @param targetNum 百分比计算的分母
     * @return
     */
    public static String getPercentageAndAverage(List<String> list, int targetNum){

        Double numD = 0.0;

        for (String strNum: list){
            numD += Double.parseDouble(strNum);
        }

        Double average = numD/list.size();
        Double percentage = average / targetNum;
        String percentageResult = average+"("+percentage+"%)";
        list.clear();

        return percentageResult;
    }
}
