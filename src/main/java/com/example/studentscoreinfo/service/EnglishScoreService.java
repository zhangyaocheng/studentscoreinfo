package com.example.studentscoreinfo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.studentscoreinfo.mapper.ExamInfoMapper;
import com.example.studentscoreinfo.mapper.StudentExamEngScoreMapper;
import com.example.studentscoreinfo.mapper.StudentInfoMapper;
import com.example.studentscoreinfo.pojo.*;
import com.example.studentscoreinfo.util.ListUtil;
import com.example.studentscoreinfo.util.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.Style;
import java.util.*;

/**
 * 英语成绩服务
 */
@Slf4j
@Service
public class EnglishScoreService {

    @Autowired
    private StudentExamEngScoreMapper mapper;
    @Autowired
    private ExamInfoMapper examInfoMapper;
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    public ResourceBundle bundle = ResourceBundle.getBundle("application", new Locale("CN"));
    private Integer englishLis = Integer.parseInt(bundle.getString("englishscore.lis"));
    private Integer englishSin = Integer.parseInt(bundle.getString("englishscore.sin"));
    private Integer englishClo = Integer.parseInt(bundle.getString("englishscore.clo"));
    private Integer englishRC = Integer.parseInt(bundle.getString("englishscore.rc"));
    private Integer englishGapf = Integer.parseInt(bundle.getString("englishscore.gapf"));
    private Integer englishRcf = Integer.parseInt(bundle.getString("englishscore.rcf"));
    private Integer englishWri = Integer.parseInt(bundle.getString("englishscore.wri"));
    private Integer englishTotal = Integer.parseInt(bundle.getString("englishscore.total"));



    /**
     * 转换 将前端传递过来的学生成绩信息 转换为单纯的考试成绩信息
     * @param studentExamEnglishScore
     * @param StudentId
     * @param ExamId
     * @return
     */
    public Englishscoreinfo transSEES2ESI(StudentExamEnglishScore studentExamEnglishScore,
                                                 Integer StudentId, Integer ExamId){
        Englishscoreinfo englishscoreinfo = new Englishscoreinfo();
        englishscoreinfo.setType(studentExamEnglishScore.getType());
        englishscoreinfo.setListening(studentExamEnglishScore.getListening());
        englishscoreinfo.setSinglechoice(studentExamEnglishScore.getSinglechoice());
        englishscoreinfo.setClozetest(studentExamEnglishScore.getClozetest());
        englishscoreinfo.setReadcomphrehense(studentExamEnglishScore.getReadcomphrehense());
        englishscoreinfo.setGapfiling(studentExamEnglishScore.getGapfiling());
        englishscoreinfo.setRcfiling(studentExamEnglishScore.getRcfiling());
        englishscoreinfo.setWriting(studentExamEnglishScore.getWriting());
        englishscoreinfo.setTotal(studentExamEnglishScore.getTotal());
        englishscoreinfo.setExamid(ExamId);
        englishscoreinfo.setStudentid(StudentId);

        return englishscoreinfo;
    }

    /**
     * 学生编码和考试名称获取对应的学生ID和考试信息ID
     * @param studentNum
     * @param examName
     * @return
     */
    public List<Integer> getIDList(String studentNum, String examName, Msg result){
        List<Integer> idList = new ArrayList<>();

        StudentInfo studentInfo = studentInfoMapper.findByStudentNumber(studentNum);
        if (studentInfo==null){
            log.error("学号:{} 对应的学生信息不存在", studentNum);
            result.setErrMsg("学号:"+studentNum+"对应的学生信息不存在");
        }else {
            idList.add(studentInfo.getId());
        }
        ExamInfo examInfo = examInfoMapper.findByExamName(examName);
        if (examInfo==null){
            log.error("考试名称{}, 对应的考试信息不存在", examName);
            result.setErrMsg("考试名称"+examName+"对应的考试信息不存在");
        }else {
            idList.add(examInfo.getId());
        }

        return idList;
    }

    public Englishscoreinfo getEnglishScoreInfo(StudentExamEnglishScore studentExamEnglishScore, Msg result){
        String studentNumber = studentExamEnglishScore.getStudentnumber();
        String examName = studentExamEnglishScore.getExamname();

        List<Integer> idList = getIDList(studentNumber, examName, result);
        // 学生/考试信息有一个缺失
        if (idList.size()<2){
            return null;
        }

        Integer studentId = idList.get(0);
        Integer examId = idList.get(1);

        Englishscoreinfo englishscoreinfo = transSEES2ESI(studentExamEnglishScore, studentId, examId);
        Englishscoreinfo englishscoreinfo_fromdb = mapper.findByExamIdAndStudentId(studentId, examId);
        if (englishscoreinfo_fromdb==null){
            englishscoreinfo.setIsDelete(0);
        }else {
            englishscoreinfo.setId(englishscoreinfo_fromdb.getId());
            englishscoreinfo.setIsDelete(englishscoreinfo_fromdb.getIsDelete());
        }
        return englishscoreinfo;
    }

    /** 新增学生成绩
     * @param studentExamEnglishScore
     * @return
     */
    public Msg addEnglishScore(StudentExamEnglishScore studentExamEnglishScore){
        Msg result = new Msg();
        result.setOk(false);
        result.setStatus("500");
        result.setData(null);

        try {

            Englishscoreinfo englishscoreinfo = getEnglishScoreInfo(studentExamEnglishScore, result);
            if (englishscoreinfo==null){
                return result;
            }

            Englishscoreinfo esi = mapper.findByUnique(englishscoreinfo);
            if (esi!=null){
                result.setErrMsg("该学生考试信息已经录入");
                return result;
            }
            mapper.addEnglishExam(englishscoreinfo);

            Englishscoreinfo esi2 = mapper.findByUnique(englishscoreinfo);
            result.setData(esi2);
            result.setStatus("200");
            result.setOk(true);

        }catch (Exception e){
            result.setExceptionMsg("新增学生成绩异常");
            log.error("新增学生成绩异常", e);
        }

        return result;
    }

    /**
     * 编辑学生成绩
     * @param studentExamEnglishScore
     * @return
     */
    public Msg editEnglishScore(StudentExamEnglishScore studentExamEnglishScore){
        Msg result = new Msg();
        result.setOk(false);
        result.setStatus("50");
        result.setData(null);

        try {

            Englishscoreinfo englishscoreinfo = getEnglishScoreInfo(studentExamEnglishScore, result);
            if (englishscoreinfo == null){return result;}

            Englishscoreinfo esi = mapper.findByUnique(englishscoreinfo);
            if (esi!=null && esi.getId()!=englishscoreinfo.getId()){
                result.setErrMsg("修改的学生信息已存在，请注意查看考试名称，学生学号是否重复填写");
                return result;
            }
            mapper.editEnglishExam(englishscoreinfo);
            Englishscoreinfo esi2 = mapper.findByUnique(englishscoreinfo);
            result.setData(esi2);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("编辑学生成绩异常");
            log.error("编辑学生成绩异常", e);
        }

        return result;
    }

    /**
     * 删除学生成绩
     * @param studentExamEnglishScore
     * @return
     */
    public Msg deleteEnglishScore(StudentExamEnglishScore studentExamEnglishScore){

        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            Englishscoreinfo englishscoreinfo = getEnglishScoreInfo(studentExamEnglishScore, result);
            if (englishscoreinfo==null) {
                return result;
            }

            Englishscoreinfo esi = mapper.findByUnique(englishscoreinfo);
            if (esi==null){
                result.setErrMsg("待删除数据不存在与数据库中");
                return result;
            }
            englishscoreinfo.setIsDelete(1);
            mapper.editEnglishExam(englishscoreinfo);
            Englishscoreinfo esi2 = mapper.findByUniqueWithOutDelete(englishscoreinfo);
            result.setData(esi2);
            result.setOk(false);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("删除学生成绩异常");
            log.error("删除学生成绩异常", e);
        }

        return result;
    }

    /**
     * 获取所有学生成绩
     * @param pageNum
     * @param size
     * @return
     */
    public Msg findAll(int pageNum, int size){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            PageHelper.startPage(pageNum, size);
            List<StudentExamEnglishScore> list = mapper.findAll();
            PageInfo<StudentExamEnglishScore> pageInfo = new PageInfo<>(list);

            result.setData(PageUtils.getPageResult(pageInfo));
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("获取所有学生信息异常");
            log.error("获取所有学生信息异常", e);
        }

        return result;
    }

    /**
     * 通过参数获取学生成绩
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
            if (pageNum==null){pageNum = 1;}
            if (size == null){size=10;}

            Map map = new HashMap();
            map.put("type", json.getString("type"));
            map.put("examname", json.getString("examname"));
            map.put("name", json.getString("name"));
            map.put("studentnumber", json.getString("studentnumber"));
            map.put("sgrade", json.getString("sgrade"));
            map.put("sclass", json.getString("sclass"));
            map.put("examtime", json.getString("examtime"));

            PageHelper.startPage(pageNum, size);
            List<StudentExamEnglishScore> list = mapper.findByParameter(map);
            PageInfo<StudentExamEnglishScore> pageInfo = new PageInfo<>(list);
            result.setData(PageUtils.getPageResult(pageInfo));
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("通过参数获取学生成绩异常");
            log.error("通过参数获取学生成绩异常", e);
        }


        return result;
    }

    /**
     * 通过ID获取学生考试成绩
     * @param id
     * @return
     */
    public Msg findById(Integer id){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            StudentExamEnglishScore studentExamEnglishScore = mapper.findById(id.toString());
            if (studentExamEnglishScore==null){
                result.setErrMsg("通过ID:"+id+" 对应的学生成绩不在数据库中");
                return result;
            }
            result.setData(studentExamEnglishScore);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("通过ID获取学生考试成绩异常");
            log.error("通过ID获取学生考试成绩异常", e);
        }

        return result;
    }

    /**
     * 获取一个学生在指定时间范围内考试成绩分布
     * @param json
     * @return
     */
    public Msg getStudentScoreListByTime(JSONObject json){

        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            String startTime = json.getString("startTime");
            String endTime = json.getString("endTime");
            String studentNumber = json.getString("studentnumber");
            Map map = new HashMap();
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            map.put("studentnumber", studentNumber);

            List<StudentExamEnglishScore> lists = mapper.findScoreBetweenTime(map);
            List<String> lisningList = new ArrayList<>(); // 听力成绩列表
            List<String> singlechoiceList = new ArrayList<>(); // 单选成绩列表
            List<String> clozetestList = new ArrayList<>(); //完型填空列表
            List<String> readcomphrehenseList = new ArrayList<>(); //阅读理解列表
            List<String> gapfilingList = new ArrayList<>(); //选词填空列表
            List<String> rcfilingList = new ArrayList<>(); //阅读理解填词
            List<String> writing = new ArrayList<>(); //写作列表
            List<String> total = new ArrayList<>(); //总分列表
            List<String> examname = new ArrayList<>(); //考试名称列表

            for (StudentExamEnglishScore info: lists){
                lisningList.add(info.getListening());
                singlechoiceList.add(info.getSinglechoice());
                clozetestList.add(info.getClozetest());
                readcomphrehenseList.add(info.getReadcomphrehense());
                gapfilingList.add(info.getGapfiling());
                rcfilingList.add(info.getRcfiling());
                writing.add(info.getWriting());
                total.add(info.getTotal());
                examname.add(info.getExamname());
            }
            List<List<String>> allInfos = new ArrayList<>();
            allInfos.add(lisningList);
            allInfos.add(singlechoiceList);
            allInfos.add(clozetestList);
            allInfos.add(readcomphrehenseList);
            allInfos.add(gapfilingList);
            allInfos.add(rcfilingList);
            allInfos.add(writing);
            allInfos.add(total);
            allInfos.add(examname);

            result.setData(allInfos);
            result.setOk(true);
            result.setStatus("200");


        }catch (Exception e){
            result.setExceptionMsg("获取一段时间内学生成绩列表异常");
            log.error("获取一段时间内学生成绩列表异常", e);
        }

        return result;
    }

    /**
     * 获取一个学生在若干次考试内考试成绩分布
     * @param json
     * @return
     */
    public Msg getStudentScoreListByExam(JSONObject json){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            String examList = json.getString("examList");
            String studentNumber = json.getString("studentnumber");
            List<String> examLists = new ArrayList<>(); // 存放考试名称信息列表
            if (examList.contains(",")){
                String[] exams = examList.split(",");
                for (String exam:exams){
                    examLists.add(exam);
                }
            }else {
                examLists.add(examList);
            }


            List<String> lisningList = new ArrayList<>(); // 听力成绩列表
            List<String> singlechoiceList = new ArrayList<>(); // 单选成绩列表
            List<String> clozetestList = new ArrayList<>(); //完型填空列表
            List<String> readcomphrehenseList = new ArrayList<>(); //阅读理解列表
            List<String> gapfilingList = new ArrayList<>(); //选词填空列表
            List<String> rcfilingList = new ArrayList<>(); //阅读理解填词
            List<String> writing = new ArrayList<>(); //写作列表
            List<String> total = new ArrayList<>(); //总分列表
            List<String> examname = new ArrayList<>(); //考试名称列表

            for (String exam: examLists){
                Map map = new HashMap();
                map.put("studentnumber",studentNumber);
                map.put("examname",exam);
                StudentExamEnglishScore info = mapper.findByExamnameAndStudentNum(map);
                lisningList.add(info.getListening());
                singlechoiceList.add(info.getSinglechoice());
                clozetestList.add(info.getClozetest());
                readcomphrehenseList.add(info.getReadcomphrehense());
                gapfilingList.add(info.getGapfiling());
                rcfilingList.add(info.getRcfiling());
                writing.add(info.getWriting());
                total.add(info.getTotal());
                examname.add(info.getExamname());
            }

            List<List<String>> allInfos = new ArrayList<>();
            allInfos.add(lisningList);
            allInfos.add(singlechoiceList);
            allInfos.add(clozetestList);
            allInfos.add(readcomphrehenseList);
            allInfos.add(gapfilingList);
            allInfos.add(rcfilingList);
            allInfos.add(writing);
            allInfos.add(total);
            allInfos.add(examname);

            result.setData(allInfos);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("获取若干次考试学生成绩列表异常");
            log.error("获取若干次考试学生成绩列表异常", e);
        }

        return result;
    }

    /**
     * 过去某一次考试 某一个学生各类型平均分的百分比分布
     * @param json
     * @return
     */
    public Msg getScorePercentageByExam(JSONObject json){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            String studentNumber = json.getString("studentnumber");
            String examname = json.getString("examname");
            Map map = new HashMap();
            map.put("studentnumber", studentNumber);
            map.put("examname", examname);
            StudentExamEnglishScore score = mapper.findByExamnameAndStudentNum(map);

            List<String> percentageList = new ArrayList<>();
            percentageList.add(Float.parseFloat(score.getListening()) / englishLis + "");
            percentageList.add(Float.parseFloat(score.getSinglechoice()) / englishSin + "");
            percentageList.add(Float.parseFloat(score.getClozetest())/englishClo + "");
            percentageList.add(Float.parseFloat(score.getReadcomphrehense())/englishRC + "");
            percentageList.add(Float.parseFloat(score.getGapfiling())/englishGapf + "");
            percentageList.add(Float.parseFloat(score.getRcfiling())/englishRcf + "");
            percentageList.add(Float.parseFloat(score.getWriting())/englishWri +"");
            percentageList.add(Float.parseFloat(score.getTotal())/englishTotal+"");

            result.setData(percentageList);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("获取学生在某一次考试的百分比异常");
            log.error("获取学生在某一次考试的百分比异常", e);
        }

        return result;
    }

    /**
     * 获取一段时间内 某一年级某一班级在各类型题目的平均分和他们的百分比
     * @param json
     * @return
     */
    public Msg getScorePercentageByGradeAndClass(JSONObject json){

        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            Map map = new HashMap();
            map.put("sgrade", json.getString("sgrade"));
            map.put("sclass", json.getString("sclass"));
            map.put("startTime", json.getString("startTime"));
            map.put("endTime", json.getString("endTime"));

            //获取所有成绩信息
            List<StudentExamEnglishScore> lists = mapper.findByGradeAndClassBetweenTime(map);
            List<String> examNameList = mapper.findExamNameList(map);

            List<String> lisningList_tmp = new ArrayList<>(); // 听力成绩列表
            List<String> singlechoiceList_tmp = new ArrayList<>(); // 单选成绩列表
            List<String> clozetestList_tmp = new ArrayList<>(); //完型填空列表
            List<String> readcomphrehenseList_tmp = new ArrayList<>(); //阅读理解列表
            List<String> gapfilingList_tmp = new ArrayList<>(); //选词填空列表
            List<String> rcfilingList_tmp = new ArrayList<>(); //阅读理解填词
            List<String> writing_tmp = new ArrayList<>(); //写作列表
            List<String> total_tmp = new ArrayList<>(); //总分列表

            List<String> lisningList = new ArrayList<>(); // 听力成绩列表
            List<String> singlechoiceList = new ArrayList<>(); // 单选成绩列表
            List<String> clozetestList = new ArrayList<>(); //完型填空列表
            List<String> readcomphrehenseList = new ArrayList<>(); //阅读理解列表
            List<String> gapfilingList = new ArrayList<>(); //选词填空列表
            List<String> rcfilingList = new ArrayList<>(); //阅读理解填词
            List<String> writing = new ArrayList<>(); //写作列表
            List<String> total = new ArrayList<>(); //总分列表
            List<String> examname = new ArrayList<>(); //考试名称列表

            for (String examnameInfo: examNameList){
                for (StudentExamEnglishScore info: lists){
                    if (info.getExamname().equalsIgnoreCase(examnameInfo)){
                        lisningList_tmp.add(info.getListening());
                        singlechoiceList_tmp.add(info.getSinglechoice());
                        clozetestList_tmp.add(info.getClozetest());
                        readcomphrehenseList_tmp.add(info.getReadcomphrehense());
                        gapfilingList_tmp.add(info.getGapfiling());
                        rcfilingList_tmp.add(info.getRcfiling());
                        writing_tmp.add(info.getWriting());
                        total_tmp.add(info.getTotal());
                    }
                }
                examname.add(examnameInfo);
                String percentageResultListening = ListUtil.getPercentageAndAverage(lisningList_tmp, englishLis);
                lisningList.add(percentageResultListening);

                String percentageResultSingleChoice = ListUtil.getPercentageAndAverage(singlechoiceList_tmp, englishSin);
                singlechoiceList.add(percentageResultSingleChoice);

                String percentageResultCloze = ListUtil.getPercentageAndAverage(clozetestList_tmp, englishClo);
                clozetestList.add(percentageResultCloze);

                String percentageResultReadcomlist = ListUtil.getPercentageAndAverage(readcomphrehenseList_tmp, englishRC);
                readcomphrehenseList.add(percentageResultReadcomlist);

                String percentageResultGap = ListUtil.getPercentageAndAverage(gapfilingList_tmp, englishGapf);
                gapfilingList.add(percentageResultGap);

                String percentageResultRCF = ListUtil.getPercentageAndAverage(rcfilingList_tmp, englishRcf);
                rcfilingList.add(percentageResultRCF);

                String percentageResultTotal = ListUtil.getPercentageAndAverage(total_tmp, englishTotal);
                total.add(percentageResultTotal);

                String percentageResultWrite = ListUtil.getPercentageAndAverage(writing_tmp, englishWri);
                writing.add(percentageResultWrite);

            }

            List<List<String>> allInfos = new ArrayList<>();
            allInfos.add(lisningList);
            allInfos.add(singlechoiceList);
            allInfos.add(clozetestList);
            allInfos.add(readcomphrehenseList);
            allInfos.add(gapfilingList);
            allInfos.add(rcfilingList);
            allInfos.add(writing);
            allInfos.add(total);
            allInfos.add(examname);

            result.setData(allInfos);
            result.setOk(true);
            result.setStatus("200");


        }catch (Exception e){
            result.setExceptionMsg("获取一段时间内 某一年级某一班级在各类型题目的平均分和他们的百分比异常");
            log.error("获取一段时间内 某一年级某一班级在各类型题目的平均分和他们的百分比异常", e);
        }

        return result;
    }

    /**
     * 获取某一次考试 某年级某班其人数得分区间
     * @param json
     * @return
     */
    public Msg getScoreArea(JSONObject json){
        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            Map map = new HashMap();
            map.put("sgrade", json.getString("sgrade"));
            map.put("sclass", json.getString("sclass"));
            map.put("examname", json.getString("examname"));
            List<ScoreArea> list = mapper.findScoreAreaByParameter(map);
            List<String> scoreArea = new ArrayList<>(); // 区间信息
            List<String> number = new ArrayList<>(); // 人数信息

            for (ScoreArea scoreArea1: list){
                scoreArea.add(scoreArea1.getScoreArea());
                number.add(scoreArea1.getNumber()+"");
            }
            List<List<String>> allInfo = new ArrayList<>();
            allInfo.add(scoreArea);
            allInfo.add(number);

            result.setData(allInfo);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("获取分数区间异常");
            log.error("获取分数区间异常");
        }

        return result;
    }

    /**
     * 每一年级某一班级在若干次考试内 其各个类型题目平均分及其百分比
     * @param json
     * @return
     */
    public Msg getScorePercentageInGradeAndClassByExam(JSONObject json){

        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {

            String sgrade = json.getString("sgrade");
            String sclass = json.getString("sclass");
            String examList = json.getString("examList");
            List<String> examLists = new ArrayList<>(); // 存放考试名称信息列表
            if (examList.contains(",")){
                String[] exams = examList.split(",");
                for (String exam:exams){
                    examLists.add(exam);
                }
            }else {
                examLists.add(examList);
            }

            List<String> lisningList_tmp = new ArrayList<>(); // 听力成绩列表
            List<String> singlechoiceList_tmp = new ArrayList<>(); // 单选成绩列表
            List<String> clozetestList_tmp = new ArrayList<>(); //完型填空列表
            List<String> readcomphrehenseList_tmp = new ArrayList<>(); //阅读理解列表
            List<String> gapfilingList_tmp = new ArrayList<>(); //选词填空列表
            List<String> rcfilingList_tmp = new ArrayList<>(); //阅读理解填词
            List<String> writing_tmp = new ArrayList<>(); //写作列表
            List<String> total_tmp = new ArrayList<>(); //总分列表

            List<String> lisningList = new ArrayList<>(); // 听力成绩列表
            List<String> singlechoiceList = new ArrayList<>(); // 单选成绩列表
            List<String> clozetestList = new ArrayList<>(); //完型填空列表
            List<String> readcomphrehenseList = new ArrayList<>(); //阅读理解列表
            List<String> gapfilingList = new ArrayList<>(); //选词填空列表
            List<String> rcfilingList = new ArrayList<>(); //阅读理解填词
            List<String> writing = new ArrayList<>(); //写作列表
            List<String> total = new ArrayList<>(); //总分列表
            List<String> examname = new ArrayList<>(); //考试名称列表

            for (String exam:examLists){
                List<StudentExamEnglishScore> lists = mapper.findByExam(exam);
                for (StudentExamEnglishScore info:lists){
                    lisningList_tmp.add(info.getListening());
                    singlechoiceList_tmp.add(info.getSinglechoice());
                    clozetestList_tmp.add(info.getClozetest());
                    readcomphrehenseList_tmp.add(info.getReadcomphrehense());
                    gapfilingList_tmp.add(info.getGapfiling());
                    rcfilingList_tmp.add(info.getRcfiling());
                    writing_tmp.add(info.getWriting());
                    total_tmp.add(info.getTotal());
                }

                examname.add(exam);
                String percentageResultListening = ListUtil.getPercentageAndAverage(lisningList_tmp, englishLis);
                lisningList.add(percentageResultListening);

                String percentageResultSingleChoice = ListUtil.getPercentageAndAverage(singlechoiceList_tmp, englishSin);
                singlechoiceList.add(percentageResultSingleChoice);

                String percentageResultCloze = ListUtil.getPercentageAndAverage(clozetestList_tmp, englishClo);
                clozetestList.add(percentageResultCloze);

                String percentageResultReadcomlist = ListUtil.getPercentageAndAverage(readcomphrehenseList_tmp, englishRC);
                readcomphrehenseList.add(percentageResultReadcomlist);

                String percentageResultGap = ListUtil.getPercentageAndAverage(gapfilingList_tmp, englishGapf);
                gapfilingList.add(percentageResultGap);

                String percentageResultRCF = ListUtil.getPercentageAndAverage(rcfilingList_tmp, englishRcf);
                rcfilingList.add(percentageResultRCF);

                String percentageResultTotal = ListUtil.getPercentageAndAverage(total_tmp, englishTotal);
                total.add(percentageResultTotal);

                String percentageResultWrite = ListUtil.getPercentageAndAverage(writing_tmp, englishWri);
                writing.add(percentageResultWrite);

            }

            List<List<String>> allInfos = new ArrayList<>();
            allInfos.add(lisningList);
            allInfos.add(singlechoiceList);
            allInfos.add(clozetestList);
            allInfos.add(readcomphrehenseList);
            allInfos.add(gapfilingList);
            allInfos.add(rcfilingList);
            allInfos.add(writing);
            allInfos.add(total);
            allInfos.add(examname);

            result.setData(allInfos);
            result.setOk(true);
            result.setStatus("200");

        }catch (Exception e){
            result.setExceptionMsg("获取某年级某班在若干次考试其题目平均分和百分比异常");
            log.error("获取某年级某班在若干次考试其题目平均分和百分比异常", e);
        }

        return result;
    }

}
