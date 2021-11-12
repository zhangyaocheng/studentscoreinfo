package com.example.studentscoreinfo.mapper;

import com.example.studentscoreinfo.pojo.ExamInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ExamInfoMapper {

    int addExam(ExamInfo examInfo);
    int editExam(ExamInfo examInfo);

    ExamInfo findByUnique(ExamInfo examInfo);
    ExamInfo findByUniqueWithOutDelete(ExamInfo examInfo);
    ExamInfo findByExamName(String examname);
    ExamInfo findById(String id);
    List<ExamInfo> findByNameFuzy(String examname); //通过模糊名称获取考试信息列表

    List<ExamInfo> findAll();
    List<ExamInfo> findByParameter(Map map);

}
