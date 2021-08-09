package com.example.studentscoreinfo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.mapper.ExamInfoMapper;
import com.example.studentscoreinfo.pojo.ExamInfo;
import com.example.studentscoreinfo.pojo.Msg;
import com.example.studentscoreinfo.util.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考试信息服务
 */
@Slf4j
@Service
public class ExamService {

    @Autowired
    private ExamInfoMapper examInfoMapper;

    /**
     * 添加考试信息
     * @param examInfo 考试消息体
     * @return 返回Msg 类型数据
     */
    public Msg addExam(ExamInfo examInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            ExamInfo ei = examInfoMapper.findByUnique(examInfo);
            if (ei!=null){
                result.setErrMsg("考试信息已经存在于数据库中");
                return result;
            }
            examInfo.setIsDelete(0);
            examInfoMapper.addExam(examInfo);

            ExamInfo ei2 = examInfoMapper.findByUnique(examInfo);
            result.setData(ei2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("新增考试信息异常");
            log.error("新增考试信息异常", e);
        }

        return result;
    }

    /**
     * 编辑考试信息
     * @param examInfo
     * @return
     */
    public Msg editExam(ExamInfo examInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            ExamInfo ei = examInfoMapper.findByUnique(examInfo);
            if (ei!=null && ei.getId()!=examInfo.getId()){
                result.setErrMsg("数据库中存在名称相同的考试信息");
                return result;
            }
            examInfoMapper.editExam(examInfo);
            ExamInfo ei2 = examInfoMapper.findByUnique(examInfo);
            result.setData(ei2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("编辑考试信息异常");
            return result;
        }

        return result;
    }

    /**
     * 删除考试信息
     * @param examInfo
     * @return
     */
    public Msg deleteExam(ExamInfo examInfo){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            ExamInfo ei = examInfoMapper.findByUnique(examInfo);
            if (ei ==null){
                result.setErrMsg("该考试信息已被删除");
                return result;
            }
            examInfo.setIsDelete(1);
            examInfoMapper.editExam(examInfo);
            ExamInfo ei2 = examInfoMapper.findByUniqueWithOutDelete(examInfo);
            result.setData(ei2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("删除考试信息异常");
            log.error("删除考试信息异常", e);
        }

        return result;
    }

    /**
     * 返回所有考试信息
     * @param pageNum
     * @param size
     * @return
     */
    public Msg findAll(int pageNum, int size){
        Msg result= new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            PageHelper.startPage(pageNum, size);
            List<ExamInfo> lists = examInfoMapper.findAll();
            PageInfo<ExamInfo> pageInfo = new PageInfo<>(lists);

            result.setData(PageUtils.getPageResult(pageInfo));
            result.setOk(true);
            result.setErrMsg("200");

        }catch (Exception e){
            result.setExceptionMsg("获取所有考试信息异常");
            log.error("获取所有考试信息异常", e);
        }

        return result;
    }

    /**
     * 通过参数获取考试信息
     * @param json
     * @return
     */
    public Msg findByParameter(JSONObject json){
        Msg result = new Msg();
        result.setOk(false);
        result.setData(null);
        result.setStatus("500");

        try {

            Integer pageNum = json.getInteger("pageNum");
            Integer size = json.getInteger("size");

            if (pageNum==null){
                pageNum = 1;
            }
            if (size == null){
                size = 10;
            }

            Map map = new HashMap();
            map.put("examtime", json.getString("examtime"));
            map.put("examname", json.getString("examname"));
            map.put("remark", json.getString("remark"));
            map.put("isDelete", json.getInteger("isDelete"));

            PageHelper.startPage(pageNum, size);
            List<ExamInfo> list = examInfoMapper.findByParameter(map);
            PageInfo<ExamInfo> pageInfo = new PageInfo<>(list);

            result.setData(PageUtils.getPageResult(pageInfo));
            result.setStatus("500");
            result.setOk(true);

        }catch (Exception e){
            result.setExceptionMsg("通过参数获取考试信息异常");
            log.error("通过参数获取考试信息异常", e);
        }

        return result;
    }

}
