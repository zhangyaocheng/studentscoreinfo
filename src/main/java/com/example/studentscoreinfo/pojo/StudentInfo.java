package com.example.studentscoreinfo.pojo;

import lombok.Data;
import lombok.NonNull;

/**
 * 学生信息表
 */
@Data
public class StudentInfo {

    private Integer id; //学生id
    @NonNull
    private String studentnumber; // 学号
    @NonNull
    private String name; // 姓名
    private Integer age; // 年龄
    private String sex; // 性别
    private String time2school; // 入学时间
    private String sgrade; // 所在年级
    private String sclass; // 所在班级
    private Integer isgraduated; // 是否毕业 0 没有毕业 1 已经毕业
    private String remark; // 备注
    private String relatepeople; // 联系人
    private String telephone; // 联系电话
    private Integer isDelete;

}
