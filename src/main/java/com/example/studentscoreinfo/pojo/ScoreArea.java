package com.example.studentscoreinfo.pojo;

import lombok.Data;

/**
 * 分区人数区间
 */
@Data
public class ScoreArea {

    private Integer number; // 位于该区间的人数
    private String scoreArea; // 区间名称

}
