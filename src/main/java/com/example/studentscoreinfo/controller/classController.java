package com.example.studentscoreinfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.pojo.ClassInfo;
import com.example.studentscoreinfo.pojo.Msg;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 班级信息控制器
 */

@RequestMapping("/class")
@RestController
public class classController {

    /**
     * 新增班级信息 和年级信息关联
     * @param classInfo
     * @return
     */
    @PostMapping("/addClass")
    public Msg addClassInfo(@RequestBody ClassInfo classInfo){
        return null;
    }

    /**
     * 删除班级信息 通过年级信息和班级信息进行唯一索引 使用伪删除 将isDelete设置为1表示删除
     * @param classInfo
     * @return
     */
    @PostMapping("/deleteClassInfo")
    public Msg deleteClassInfo(@RequestBody ClassInfo classInfo){
        return null;
    }

    /**
     * 修改班级信息
     * @param classInfo
     * @return
     */
    @PostMapping("/updateClassInfo")
    public Msg updateClassInfo(@RequestBody ClassInfo classInfo){
        return null;
    }

    /**
     * 通过参数获取班级信息
     * @param json
     * @return
     */
    @PostMapping("/findByParameters")
    public Msg findClassInfoByParameters(@RequestBody JSONObject json){
        return null;
    }




}
