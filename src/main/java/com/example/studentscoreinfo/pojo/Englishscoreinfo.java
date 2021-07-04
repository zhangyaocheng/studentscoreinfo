package com.example.studentscoreinfo.pojo;

import lombok.Data;

/**
 * 英语成绩表展示
 */
@Data
public class Englishscoreinfo {

    private String type; // 类型 模型英语
    private String listening; // 听力分数 这里用字符串表示
    private String singlechoice; // 单选
    private String clozetest; // 完形填空
    private String readcomprehense; // 阅读理解
    private String gapfiling; //选词填空
    private String rcfiling; //阅读理解填空
    private String writing; //作文
    private String total; // 总分
    private Integer examid; // 考试id
    private Integer studentid; // 学生信息id
    private Integer isDelete;


}
