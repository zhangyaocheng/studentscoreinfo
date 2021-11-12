package com.example.studentscoreinfo.pojo;

import lombok.Data;

/**
 * 班级信息
 */
@Data
public class ClassInfo {

    private Integer id;
    private String sgrade; // 关联的年级信息
    private String sclass; // 班级信息 一个字符串 用数字代替
    private String remark; // 备注信息
    private int isDelete;

}
