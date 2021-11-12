package com.example.studentscoreinfo.service;

import ch.qos.logback.core.ConsoleAppender;
import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.mapper.GradeinfoMapper;
import com.example.studentscoreinfo.pojo.GradeInfo;
import com.example.studentscoreinfo.pojo.Msg;
import com.example.studentscoreinfo.util.IntegerUtil;
import com.example.studentscoreinfo.util.PageUtils;
import com.example.studentscoreinfo.util.StringUtil;
import com.github.pagehelper.Page;
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
public class GradeService {

    @Autowired
    private GradeinfoMapper gradeinfoMapper;


    /**
     * 新增grade信息
     * @param gradeInfo
     * @return
     */
    public Msg addGrade(GradeInfo gradeInfo){
        Msg result = new Msg();
        result.setOk(false);
        result.setStatus("500");
        result.setData(null);

        try {
            if (StringUtil.isBlink(gradeInfo.getSgrade())){
                result.setErrMsg("年级信息缺失或者为空");
                return result;
            }
            if (gradeInfo.getRemark()==null){
                gradeInfo.setRemark("");
            }
            gradeInfo.setIsDelete(0);

            GradeInfo gradeInfo1 = gradeinfoMapper.findGradeInfoByGrade(gradeInfo);
            if (gradeInfo1!=null && gradeInfo1.getIsDelete()==0){
                result.setErrMsg("该年级信息已经存在，请重新配置");
                return result;
            }

            gradeinfoMapper.addGrade(gradeInfo);

            GradeInfo gradeInfo2 = gradeinfoMapper.findGradeInfoByGrade(gradeInfo);
            result.setData(gradeInfo2);
            result.setStatus("200");
            result.setOk(true);

        }catch (Exception e){
            result.setExceptionMsg("新增年级信息异常");
            log.error("新增年级信息异常",e);
        }

        return result;
    }

    /**
     * 删除gradeInfo信息
     * @param gradeInfo
     * @return
     */
    public Msg deleteGrade(GradeInfo gradeInfo){
        Msg result = new Msg();
        result.setOk(false);
        result.setStatus("500");
        result.setData(null);

        try {

            if (gradeInfo == null){
                result.setErrMsg("gradeInfo为空");
                return result;
            }

            if (IntegerUtil.isBlink(gradeInfo.getId())){
                result.setErrMsg("id缺失或者值小于等于0");
                return result;
            }

            GradeInfo gradeInfo1 = gradeinfoMapper.findGradeInfoById(gradeInfo);
            if (gradeInfo1 == null || gradeInfo1.getIsDelete()==1){
                result.setErrMsg("该年级已经被删除");
                return result;
            }

            // 这里使用更新语法 更新isDelete语句
            gradeInfo.setIsDelete(1);
            updateGrade(gradeInfo);

            GradeInfo gradeInfo2 = gradeinfoMapper.findGradeInfoById(gradeInfo);
            result.setData(gradeInfo2);
            result.setOk(true);
            result.setStatus("200");


        }catch (Exception e){
            result.setExceptionMsg("删除年级信息异常");
            log.error("删除年级信息异常", e);
        }

        return result;
    }

    /**
     * 更新年级信息
     * @param gradeInfo
     * @return
     */
    public Msg updateGrade(GradeInfo gradeInfo){
        Msg result = new Msg();
        result.setOk(false);
        result.setData(null);
        result.setStatus("500");

        try {

            if (IntegerUtil.isBlink(gradeInfo.getId())){
                result.setErrMsg("id缺失或者值为空");
                return result;
            }

            GradeInfo gradeInfo1 = gradeinfoMapper.findGradeInfoById(gradeInfo);
            if (gradeInfo1==null){
                result.setErrMsg("id对应的年级信息不存在，请重新设置");
                return result;
            }
            gradeinfoMapper.updateGradeInfo(gradeInfo);
            GradeInfo gradeInfo2 = gradeinfoMapper.findGradeInfoById(gradeInfo);

            result.setData(gradeInfo2);
            result.setStatus("200");
            result.setOk(true);

        }catch (Exception e){
            result.setExceptionMsg("更新年级信息异常");
            log.error("更新年级信息异常",e);
        }

        return result;
    }

    /**
     * 获取所有年级信息 无需分页
     * @return
     */
    public Msg listAllGrades(){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            List<GradeInfo> list = gradeinfoMapper.listAllGrades();
            result.setData(list);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("获取所有年级信息异常");
            log.error("获取所有年级信息异常", e);
        }

        return result;
    }

    /**
     * 获取所有年级信息 需要分页
     * @param pageNum 起始页面
     * @param size 每页多少条数据
     * @return
     */
    public Msg listAllGradesWithPageHelper(int pageNum, int size){

        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {
            log.info("pageNum={}, size={}",pageNum, size);
            PageHelper.startPage(pageNum, size);
            List<GradeInfo> lists = gradeinfoMapper.listAllGrades();
            PageInfo<GradeInfo> info = new PageInfo<>(lists);
            result.setData(PageUtils.getPageResult(info));

            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("获取所有年级信息异常");
            log.error("获取所有年级信息异常", e);
        }

        return result;
    }

    /**
     * 通过参数获取年级信息 返回的是一个列表
     * @param json
     * @return
     */
    public Msg findGradesByParameter(JSONObject json){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(true);
        result.setData(null);

        try {

            Integer pageNum = json.getInteger("pageNum");
            Integer size = json.getInteger("size");
            Map map = new HashMap();
            map.put("sgrade", json.getString("sgrade"));
            map.put("remark", json.getString("remark"));

            if (IntegerUtil.isBlink(pageNum)){
                pageNum = 1;
            }
            if (IntegerUtil.isBlink(size)){
                size = 5;
            }

            PageHelper.startPage(pageNum, size);
            List<GradeInfo> list = gradeinfoMapper.findGradeInfoByParameters(map);
            PageInfo<GradeInfo> info = new PageInfo<>(list);

            result.setData(PageUtils.getPageResult(info));
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("通过参数获取数据异常");
            log.error("通过参数获取数据异常", e);
        }

        return result;
    }

    /**
     * 通过id获取对象
     * @param id
     * @return
     */
    public Msg findById(String id){

        Msg result = new Msg();
        result.setData(null);
        result.setOk(false);
        result.setStatus("500");

        try {

            GradeInfo gradeInfo = gradeinfoMapper.findById(id);
            if (gradeInfo==null){
                result.setErrMsg("通过ID获取年级信息失败");
                return result;
            }
            result.setData(gradeInfo);
            result.setStatus("200");
            result.setOk(true);

        }catch (Exception e){
            result.setExceptionMsg("通过ID获取对象异常");
            log.error("通过ID获取对象异常", e);
        }

        return result;
    }

}
