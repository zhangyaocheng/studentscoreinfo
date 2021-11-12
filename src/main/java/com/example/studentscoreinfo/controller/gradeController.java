package com.example.studentscoreinfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.pojo.GradeInfo;
import com.example.studentscoreinfo.pojo.Msg;
import com.example.studentscoreinfo.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 年级控制器
 */
@RequestMapping("/grade")
@RestController
public class gradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 新增年级信息
     * @param gradeInfo
     * @return
     */
    @PostMapping("/addGrade")
    public Msg addGrade(@RequestBody GradeInfo gradeInfo){
        return gradeService.addGrade(gradeInfo);
    }

    /**
     * 通过ID获取数据
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Msg findById(@RequestParam(value = "id", required = true) String id){
        return gradeService.findById(id);
    }

    /**
     * 删除年级数据
     * @param gradeInfo
     * @return
     */
    @PostMapping("/deleteGrade")
    public Msg deleteGrade(@RequestBody GradeInfo gradeInfo){
        return gradeService.deleteGrade(gradeInfo);
    }

    /**
     * 更新年级数据
     * @param gradeInfo
     * @return
     */
    @PostMapping("/updateGrade")
    public Msg updateGrade(@RequestBody GradeInfo gradeInfo){
        return gradeService.updateGrade(gradeInfo);
    }

    /**
     * 返回不分页的年级数据列表
     * @return
     */
    @PostMapping("/listGrades")
    public Msg listAllGrade(){
        return gradeService.listAllGrades();
    }

    /**
     * 返回分页年级数据列表
     * @param pageNum
     * @param size
     * @return
     */
    @GetMapping("/listGradesWithPageHelper")
    public Msg listAllGradesWithPageHelper(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "size", defaultValue = "5") int size){
        return gradeService.listAllGradesWithPageHelper(pageNum, size);
    }

    /**
     * 通过使用 sgrade或者remark字段查看年级信息 的列表
     * @param json
     * @return
     */
    @PostMapping("/findGradedByParameter")
    public Msg findGradesByParameter(@RequestBody JSONObject json){
        return gradeService.findGradesByParameter(json);
    }

}
