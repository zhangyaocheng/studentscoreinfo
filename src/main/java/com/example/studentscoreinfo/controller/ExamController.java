package com.example.studentscoreinfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.pojo.ExamInfo;
import com.example.studentscoreinfo.pojo.Msg;
import com.example.studentscoreinfo.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 考试信息控制器
 */
@RequestMapping("/exam")
@RestController
public class ExamController {

    @Autowired
    private ExamService examService;

    /**
     * 新增 考试信息
     * @param examInfo
     * @return
     */
    @PostMapping("/add")
    public Msg addExam(@RequestBody ExamInfo examInfo){
        return examService.addExam(examInfo);
    }

    /**
     * 编辑考试信息 一般修改考试名称 考试时间
     * @param examInfo
     * @return
     */
    @PostMapping("/edit")
    public Msg editExam(@RequestBody ExamInfo examInfo){
        return examService.editExam(examInfo);
    }

    /**
     * 删除考试信息 假删除
     * @param examInfo
     * @return
     */
    @PostMapping("/delete")
    public Msg deleteExam(@RequestBody ExamInfo examInfo){
        return examService.deleteExam(examInfo);
    }

    /**
     * 获取所有考试信息
     * @param pageNum
     * @param size
     * @return
     */
    @GetMapping("/listAll")
    public Msg findAll(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                       @RequestParam(value = "size", defaultValue = "10") int size){
        return examService.findAll(pageNum, size);
    }

    /**
     * 通过参数获取考试信息
     * @param json
     * @return
     */
    @PostMapping("/findByParameter")
    public Msg findByParameter(@RequestBody JSONObject json){
        return examService.findByParameter(json);
    }

    /**
     * 通过ID获取考试信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Msg findById(@RequestParam(value = "id", required = true) Integer id){
        return examService.findById(id);
    }

    // TODO: 通过模糊名称 获取完整考试名称

    /**
     * 通过名称获取考试信息 模糊查询
     * @param name
     * @return
     */
    @GetMapping("/findByName")
    public Msg findByName(@RequestParam(value = "name", required = true)String name){
        return examService.findByFuzyName(name);
    }
}
