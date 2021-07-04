package com.example.studentscoreinfo.pojo;

import lombok.Data;

@Data
public class Msg<T> {

    private String status; // 返回状态码
    private boolean isOk; // 是否成功
    private String errMsg; // 错误信息
    private String exceptionMsg; // 异常信息
    private T data; // 承载具体返回数据
}
