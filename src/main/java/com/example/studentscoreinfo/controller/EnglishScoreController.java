package com.example.studentscoreinfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.pojo.Englishscoreinfo;
import com.example.studentscoreinfo.pojo.Msg;
import com.example.studentscoreinfo.pojo.StudentExamEnglishScore;
import com.example.studentscoreinfo.service.EnglishScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 英语成绩控制器
 */
@RequestMapping("/englishScore")
@RestController
public class EnglishScoreController {

    @Autowired
    private EnglishScoreService service;

    /**
     * 新增英语考试成绩 参数为视图输入的值
     * @param studentExamEnglishScore
     * @return
     */
    @PostMapping("/add")
    public Msg addEnglishScore(@RequestBody StudentExamEnglishScore studentExamEnglishScore){
        return service.addEnglishScore(studentExamEnglishScore);
    }

    /**
     * 修改英语考试成绩 参数为视图输入的值
     * @param studentExamEnglishScore
     * @return
     */
    @PostMapping("/edit")
    public Msg editEnglishScore(@RequestBody StudentExamEnglishScore studentExamEnglishScore){
        return service.editEnglishScore(studentExamEnglishScore);
    }

    /**
     * 删除一项英语考试成绩 参数为视图输入的值
     * @param studentExamEnglishScore
     * @return
     */
    @PostMapping("/delete")
    public Msg deleteEnglishScore(@RequestBody StudentExamEnglishScore studentExamEnglishScore){
        return service.deleteEnglishScore(studentExamEnglishScore);
    }

    /**
     * 获取所有学生成绩 返回视图
     * @param pageNum
     * @param size
     * @return
     */
    @GetMapping("/listAll")
    public Msg findAll(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                       @RequestParam(value = "size", defaultValue = "10") int size){
        return service.findAll(pageNum, size);
    }

    /**
     * 通过参数获取学生成绩  返回视图结果
     * @param json
     * @return
     */
    @PostMapping("/findByParameter")
    public Msg findByParameter(@RequestBody JSONObject json){
        return service.findByParameter(json);
    }

    /**
     * 通过ID获取学生考试成绩
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Msg findById(@RequestParam(value = "id", required = true) Integer id){
        return service.findById(id);
    }


    /**
     * 获取学生在一定时间范围内 成绩和总分列表
     * @param json
     * @return
     */
    @PostMapping("/getStudentScoreListByTime")
    public Msg findStudentScoreListBetweenTime(@RequestBody JSONObject json){
        return service.getStudentScoreListByTime(json);
    }

    /**
     * 获取学生在若干次考试内 成绩和总分列表
     * @param json
     * @return
     */
    @PostMapping("/getStudentScoreListByExam")
    public Msg findStudentScoreListByExam(@RequestBody JSONObject json){
        return service.getStudentScoreListByExam(json);
    }

    /**
     * 获取某一次考试的全班各个分值的百分比
     * @param json
     * @return
     */
    @PostMapping("/getScorePercentage")
    public Msg findScorePercentageByExam(@RequestBody JSONObject json){
        return service.getScorePercentageByExam(json);
    }

    /**
     * 某一年级某一班级在一段时间内 其各个类型题目平均分及其百分比
     * 其中总分项目包含了班级在若干次考试的平均分
     * @param json
     * @return
     */
    @PostMapping("/getScorePercentageInGradeAndClass")
    public Msg findScorePercentageInGradeAndClass(@RequestBody JSONObject json){
        return service.getScorePercentageByGradeAndClass(json);
    }

    /**
     * 某一次考试 某年级某班其得分区间
     * @param json
     * @return
     */
    @PostMapping("/getScoreArea")
    public Msg findScoreArea(@RequestBody JSONObject json){
        return service.getScoreArea(json);
    }

    /**
     * 每一年级某一班级在若干次考试内 其各个类型题目平均分及其百分比
     * @param json
     * @return
     */
    @PostMapping("/getScorePercentageInGradeAndClassByExam")
    public Msg findScorePercentageInfoGradeAndClassByExam(@RequestBody JSONObject json){
        return service.getScorePercentageInGradeAndClassByExam(json);
    }

}
