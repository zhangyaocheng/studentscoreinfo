package com.example.studentscoreinfo.mapper;

import com.example.studentscoreinfo.pojo.Englishscoreinfo;
import com.example.studentscoreinfo.pojo.ScoreArea;
import com.example.studentscoreinfo.pojo.StudentExamEnglishScore;
import com.example.studentscoreinfo.util.ListUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 学生考试成绩结果映射表
 */
@Repository
@Mapper
public interface StudentExamEngScoreMapper {

    int addEnglishExam(Englishscoreinfo englishscoreinfo);
    int editEnglishExam(Englishscoreinfo englishscoreinfo);

    Englishscoreinfo findByUnique(Englishscoreinfo englishscoreinfo);
    Englishscoreinfo findByUniqueWithOutDelete(Englishscoreinfo englishscoreinfo);
    Englishscoreinfo findByExamIdAndStudentId(Integer studentid, Integer examid);
    List<StudentExamEnglishScore> findScoreBetweenTime(Map map);
    StudentExamEnglishScore findByExamnameAndStudentNum(Map map);
    List<StudentExamEnglishScore> findByGradeAndClassBetweenTime(Map map);
    List<String> findExamNameList(Map map);
    List<ScoreArea> findScoreAreaByParameter(Map map);
    List<StudentExamEnglishScore> findByExam(String examname);

    List<StudentExamEnglishScore> findAll();
    List<StudentExamEnglishScore> findByParameter(Map map);

}
