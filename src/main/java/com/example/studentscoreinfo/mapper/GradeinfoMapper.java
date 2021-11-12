package com.example.studentscoreinfo.mapper;

import com.example.studentscoreinfo.pojo.GradeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface GradeinfoMapper {

    int addGrade(GradeInfo gradeInfo);
    int deleteGrade(GradeInfo gradeInfo);
    GradeInfo findGradeInfoByGrade(GradeInfo gradeInfo);
    GradeInfo findGradeInfoById(GradeInfo gradeInfo);
    int updateGradeInfo(GradeInfo gradeInfo);
    GradeInfo findById(String id);

    List<GradeInfo> listAllGrades(); // 获取所有的年级信息

    List<GradeInfo> findGradeInfoByParameters(Map map); // 通过参数获取数据

}
