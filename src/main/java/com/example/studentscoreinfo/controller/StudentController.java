package com.example.studentscoreinfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.pojo.Msg;
import com.example.studentscoreinfo.pojo.StudentInfo;
import com.example.studentscoreinfo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生信息控制器
 */
@RequestMapping("/student")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 新增学生
     * @param studentInfo
     * @return
     */
    @PostMapping("/add")
    public Msg addStudent(@RequestBody StudentInfo studentInfo){
        return studentService.addStudent(studentInfo);
    }

    /**
     * 修改学生信息
     * @param studentInfo
     * @return
     */
    @PostMapping("/edit")
    public Msg editStudent(@RequestBody StudentInfo studentInfo){
        return studentService.editStudent(studentInfo);
    }

    /**
     * 批量修改学生信息 包括进入下一年级，毕业
     * json中存放数据如下：  studentnumber  : studentnumber    学生学号 是一个列表 逗号分隔符
     *                     isNextGrade : 0/1   是否进入下一年级
     *                     isGraduated : 0/1   是否毕业
     * @param json
     * @return
     */
    @PostMapping("/editStudentBat")
    public Msg editStudentBat(@RequestBody JSONObject json){
        return studentService.editStudentBat(json);
    }


    /**
     * 删除学生信息
     * @param studentInfo
     * @return
     */
    @PostMapping("/delete")
    public Msg deleteStudent(@RequestBody StudentInfo studentInfo){
        return studentService.deleteStudent(studentInfo);
    }

    /**
     * 获取所有学生信息
     * 分页展示
     * @param pageNum
     * @param size
     * @return
     */
    @GetMapping("/listAll")
    public Msg listAllStudentInfo(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "size", defaultValue = "10") int size){
        return studentService.findAll(pageNum, size);
    }

    /**
     * 通过参数获取学生信息
     * 包括 年级， 班级， 姓名， 学号等字段
     * @param json
     * @return
     */
    @PostMapping("/findByParameter")
    public Msg findByParameter(@RequestBody JSONObject json){
        return studentService.findByParameter(json);
    }

}
