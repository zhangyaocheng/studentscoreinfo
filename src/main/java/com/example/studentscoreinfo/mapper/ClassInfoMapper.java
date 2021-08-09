package com.example.studentscoreinfo.mapper;

import com.example.studentscoreinfo.pojo.ClassInfo;
import com.example.studentscoreinfo.pojo.GradeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ClassInfoMapper {

    int addClassInfo(ClassInfo classInfo);

    int deleteClassInfo(ClassInfo classInfo);

    int updateClassInfo(ClassInfo classInfo);

//    List<ClassInfo> findAll();

    ClassInfo findByUnique(ClassInfo classInfo);
    ClassInfo findByUniqueWithOutDelete(ClassInfo classInfo);

    List<ClassInfo> findByParameter(Map map); // 通过参数获取所有信息

}
