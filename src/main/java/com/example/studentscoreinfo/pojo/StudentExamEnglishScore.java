package com.example.studentscoreinfo.pojo;

import lombok.Data;

/**
 * 学生-考试-英语成绩 视图
 */
@Data
public class StudentExamEnglishScore {

    private int id;
    private String type; // 考试类型 这里是英语
    private String listening; // 听力分数 这里用字符串表示
    private String singlechoice; // 单选
    private String clozetest; // 完形填空
    private String readcomphrehense; // 阅读理解
    private String gapfiling; //选词填空
    private String rcfiling; //阅读理解填空
    private String writing; //作文
    private String total; // 总分
    private String name; // 姓名
    private String sgrade; // 所在年级
    private String sclass; // 所在班级
    private String studentnumber; // 学号
    private String examtime; // 考试时间
    private String examname; // 考试名称 唯一索引
}
