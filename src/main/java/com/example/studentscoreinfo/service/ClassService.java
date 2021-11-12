package com.example.studentscoreinfo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.mapper.ClassInfoMapper;
import com.example.studentscoreinfo.pojo.ClassInfo;
import com.example.studentscoreinfo.pojo.Msg;
import com.example.studentscoreinfo.util.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

            Integer pageNum = json.getInteger("pageNum");
            Integer size = json.getInteger("size");
            if (pageNum==null){pageNum = 1;}
            if (size==null){size=10;}

           Map<String, String> map = new HashMap<>();
           map.put("sgrade", json.getString("sgrade"));
           map.put("sclass", json.getString("sclass"));
           map.put("remark", json.getString("remark"));

           PageHelper.startPage(pageNum,size);

           List<ClassInfo> lists = classInfoMapper.findByParameter(map);
           PageInfo<ClassInfo> pageInfo = new PageInfo<>(lists);
           result.setData(PageUtils.getPageResult(pageInfo));
           result.setOk(true);
           result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("通过参数查询班级信息异常");
            log.error("通过参数查询班级信息异常", e);
        }

        return result;
    }

    /**
     * 通过id获取班级信息
     * @param id
     * @return
     */
    public Msg findClassById(String id){
        Msg result = new Msg();
        result.setOk(false);
        result.setStatus("500");
        result.setData(null);

        try {

            ClassInfo classInfo = classInfoMapper.findClassById(id);
            if (classInfo==null || classInfo.getIsDelete()==1){
                result.setErrMsg("ID:"+id+" 对应的班级信息不存在");
                return result;
            }
            result.setData(classInfo);
            result.setStatus("200");
            result.setOk(true);

        }catch (Exception e){
            result.setExceptionMsg("通过ID获取班级信息异常");
            log.error("通过ID获取班级信息异常", e);

        }

        return result;
    }

    /**
     * 返回所有班级信息并分页
     * @param pageNum
     * @param size
     * @return
     */
    public Msg listAll(Integer pageNum, Integer size){
        Msg result = new Msg();
        result.setOk(false);
        result.setStatus("500");
        result.setData(null);

        try {

            PageHelper.startPage(pageNum,size);
            List<ClassInfo> list = classInfoMapper.listAll();
            PageInfo<ClassInfo> pageInfo = new PageInfo<>(list);
            result.setData(PageUtils.getPageResult(pageInfo));
            result.setStatus("200");
            result.setOk(true);

        }catch (Exception e){
            result.setExceptionMsg("获取所有班级信息异常");
            log.error("获取所有班级信息异常", e);
        }

        return result;
    }

    /**
     * 获取所有年级信息不分页
     * @return
     */
    public Msg listAllWOP(){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            List<ClassInfo> list = classInfoMapper.listAll();
            result.setData(list);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){

            result.setExceptionMsg("获取所有班级信息异常");
            log.error("获取所有班级信息异常", e);
        }

        return result;
    }

}
