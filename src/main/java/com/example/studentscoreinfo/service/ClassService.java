package com.example.studentscoreinfo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.mapper.ClassInfoMapper;
import com.example.studentscoreinfo.pojo.ClassInfo;
import com.example.studentscoreinfo.pojo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ClassService {

    @Autowired
    ClassInfoMapper classInfoMapper;

    /**
     * 新增班级信息
     * @param classInfo
     * @return
     */
    public Msg addClassInfo(ClassInfo classInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            classInfo.setId(null);
            classInfo.setIsDelete(0);
            // 判断唯一性
            ClassInfo cls = classInfoMapper.findByUnique(classInfo);
            if (cls!=null){
                result.setErrMsg("该班级信息已经存在于数据库中");
                return result;
            }
            classInfoMapper.addClassInfo(classInfo);

            ClassInfo cls2 = classInfoMapper.findByUnique(classInfo);
            result.setData(cls2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("新增班级信息异常");
            log.error("新增班级信息异常", e);
        }

        return result;
    }

    /**
     * 更新班级信息
     * @param classInfo
     * @return
     */
    public Msg updateClassInfo(ClassInfo classInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            ClassInfo classInfo1 = classInfoMapper.findByUnique(classInfo);
            if (classInfo1!=null && classInfo1.getId()!=classInfo.getId()){
                result.setErrMsg("更新后的班级信息已经存在，请重新修改");
                return result;
            }

            classInfoMapper.updateClassInfo(classInfo);

            ClassInfo classInfo2 = classInfoMapper.findByUnique(classInfo);
            result.setData(classInfo2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("更新班级信息异常");
            log.error("更新班级信息异常",e);
        }

        return result;
    }

    /**
     * 删除班级信息
     * @param classInfo
     * @return
     */
    public Msg deleteClassInfo(ClassInfo classInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            ClassInfo cls = classInfoMapper.findByUnique(classInfo);
            if (cls==null){
                result.setErrMsg("该班级信息不存在数据库中, 无法删除");
                return result;
            }

            classInfo.setIsDelete(1);
            classInfoMapper.updateClassInfo(classInfo);

            ClassInfo cls2 = classInfoMapper.findByUniqueWithOutDelete(classInfo);
            result.setData(cls2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("删除班级信息异常");
            log.error("删除班级信息异常", e);
        }

        return result;
    }

    /**
     * 根据参数获取班级信息
     * @param json
     * @return
     */
    public Msg findByParameter(JSONObject json){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(true);
        result.setData(null);

        try {

           Map<String, String> map = new HashMap<>();
           map.put("gradeid", json.getString("gradeid"));
           map.put("sclass", json.getString("sclass"));
           map.put("remark", json.getString("remark"));

           List<ClassInfo> lists = classInfoMapper.findByParameter(map);
           result.setData(lists);
           result.setOk(true);
           result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("通过参数查询班级信息异常");
            log.error("通过参数查询班级信息异常", e);
        }

        return result;
    }

}
