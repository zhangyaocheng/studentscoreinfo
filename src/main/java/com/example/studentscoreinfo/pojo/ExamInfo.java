package com.example.studentscoreinfo.pojo;

import lombok.Data;

/**
 * 考试信息
 */
@Data
public class ExamInfo {

    private Integer id;
    private String examtime; // 考试时间
    private String examname; // 考试名称
    private String remark; // 备注
    private Integer isDelete;

}
