package com.example.studentscoreinfo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.mapper.StudentInfoMapper;
import com.example.studentscoreinfo.pojo.GradeInfo;
import com.example.studentscoreinfo.pojo.Msg;
import com.example.studentscoreinfo.pojo.StudentInfo;
import com.example.studentscoreinfo.util.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.style.StylerUtils;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生信息处理
 */
@Slf4j
@Service
public class StudentService {

    @Autowired
    StudentInfoMapper studentInfoMapper;

    /**
     * 新增学生信息
     * @param studentInfo
     * @return
     */
    public Msg addStudent(StudentInfo studentInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            StudentInfo si = studentInfoMapper.findByUnique(studentInfo);
            if (si !=null){
                result.setErrMsg("学生信息已经存在于数据库中");
                return result;
            }
            studentInfo.setIsgraduated(0);
            studentInfo.setIsDelete(0);
            studentInfo.setId(null);

            studentInfoMapper.addStudent(studentInfo);
            StudentInfo si2 = studentInfoMapper.findByUnique(studentInfo);
            result.setData(si2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("新增学生信息异常");
            log.error("新增学生信息异常", e);
        }

        return result;
    }

    /**
     * 编辑修改学生信息
     * @param studentInfo
     * @return
     */
    public Msg editStudent(StudentInfo studentInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            StudentInfo si = studentInfoMapper.findByUnique(studentInfo);
            if (si !=null && si.getId()!=studentInfo.getId()){
                result.setErrMsg("具有相同学号的学生信息已存在，请重新修改学生信息");
                return result;
            }

            studentInfoMapper.editStudent(studentInfo);
            StudentInfo si2 = studentInfoMapper.findByUnique(studentInfo);
            result.setData(si2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("编辑学生信息异常");
            log.error("编辑学生信息异常", e);
        }

        return result;
    }

    /**
     * 批量修改学生信息
     * json中包含有  studentnumber  : studentnumber    学生学号 是一个列表 逗号分隔符
     *                 isNextGrade : 0/1   是否进入下一年级
     *                 isGraduated : 0/1   是否毕业
     * @param json
     * @return
     */
    public Msg editStudentBat(JSONObject json){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(true);
        result.setData(null);

        try {

            String studentNumberList = json.getString("studentnumber");
            List<StudentInfo> studentInfos = new ArrayList<>();
            if (!studentNumberList.contains(",")){
                StudentInfo studentInfo = studentInfoMapper.findByStudentNumber(studentNumberList);
                studentInfos.add(studentInfo);
            }else if (studentNumberList.contains(",")){
                String[] studentNumberLists = studentNumberList.split(",");
                for (String number: studentNumberLists){
                    StudentInfo studentInfo = studentInfoMapper.findByStudentNumber(number);
                    studentInfos.add(studentInfo);
                }
            }

            // 判断执行哪一种操作
            // 进入下一个年级
            if (json.getString("isNextGrade").equalsIgnoreCase("1")){

                for (StudentInfo studentInfo: studentInfos){
                    studentInfo.setSgrade((Integer.parseInt(studentInfo.getSgrade())+1)+""); // 年级数+1
                }
            }else if (json.getString("isGraduated").equalsIgnoreCase("1")){
                // 是否毕业
                for (StudentInfo studentInfo: studentInfos){
                    studentInfo.setIsgraduated(1);
                }
            }

            // 执行批量操作
            studentInfoMapper.editStudentBat(studentInfos);
            result.setData(null);
            result.setOk(true);
            result.setStatus("200");


        }catch (Exception e){
            result.setExceptionMsg("批量编辑学生信息异常");
            log.error("批量编辑学生信息异常", e);
        }

        return result;
    }

    /**
     * 删除学生信息
     * @param studentInfo
     * @return
     */
    public Msg deleteStudent(StudentInfo studentInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(true);
        result.setData(null);

        try {

            StudentInfo si = studentInfoMapper.findByUnique(studentInfo);
            if (si==null){
                result.setErrMsg("该学生信息不存在数据库中或已背删除");
                return result;
            }

            studentInfo.setIsDelete(1);
            studentInfoMapper.editStudent(studentInfo);

            StudentInfo si2 = studentInfoMapper.findByUniqueWithOutDelete(studentInfo);
            result.setData(si2);
            result.setOk(true);
            result.setStatus("200");
        }catch (Exception e){
            result.setExceptionMsg("删除学生信息异常");
            log.error("删除学生信息异常", e);
        }

        return result;
    }

    /**
     * 获取所有学生信息 分页
     * @param pageNum
     * @param size
     * @return
     */
    public Msg findAll(int pageNum, int size){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(true);
        result.setData(null);

        try {

            PageHelper.startPage(pageNum, size);
            List<StudentInfo> lists = studentInfoMapper.findAll();
            PageInfo<StudentInfo> info = new PageInfo<>(lists);
            result.setData(PageUtils.getPageResult(info));
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("获取所有学生信息异常");
            log.error("获取所有学生信息异常", e);
        }

        return result;
    }

    /**
     * 通过参数获取 结果分页
     * @param json
     * @return
     */
    public Msg findByParameter(JSONObject json){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            Integer pageNum = json.getInteger("pageNum");
            Integer size = json.getInteger("size");
            if (pageNum==null){
                pageNum = 1;
            }
            if (size==null){
                size=10;
            }

            Map map = new HashMap();
            map.put("studentnumber", json.getString("studentnumber"));
            map.put("name", json.getString("name"));
            map.put("age", json.getInteger("age"));
            map.put("sex", json.getString("sex"));
            map.put("time2school", json.getString("time2school"));
            map.put("sgrade", json.get("sgrade"));
            map.put("sclass", json.getString("sclass"));
            map.put("isgraduated", json.getInteger("isgraduated"));
            map.put("remark", json.getString("remark"));
            map.put("relatepeople", json.getString("relatepeople"));
            map.put("telephone", json.getString("telephone"));
            map.put("isDelete", json.getInteger("isDelete"));

            PageHelper.startPage(pageNum,size);
            List<StudentInfo> lists = studentInfoMapper.findByParameter(map);
            PageInfo<StudentInfo> pageInfo = new PageInfo<>(lists);
            result.setData(PageUtils.getPageResult(pageInfo));
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("通过参数获取学生信息异常");
            log.error("通过参数获取学生信息异常", e);
        }

        return result;
    }

}
