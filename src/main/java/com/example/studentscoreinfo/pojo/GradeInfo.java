package com.example.studentscoreinfo.pojo;

import lombok.Data;

/**
 * 年级信息
 */
@Data
public class GradeInfo {

    private Integer id;
    private String sgrade; //年级
    private String remark; // 备注
    private Integer isDelete;

}
