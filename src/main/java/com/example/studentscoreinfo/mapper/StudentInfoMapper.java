package com.example.studentscoreinfo.mapper;

import com.example.studentscoreinfo.pojo.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 执行学生相关信息的数据库操作
 */
@Mapper
@Repository
public interface StudentInfoMapper {

    int addStudent(StudentInfo studentInfo);
    int deleteStudent(StudentInfo studentInfo);
    int editStudent(StudentInfo studentInfo);
    int editStudentBat(List<StudentInfo> list); //批量修改学生信息 包括 所在年级;是否毕业等

    StudentInfo findByUnique(StudentInfo studentInfo); // 查找唯一对象
    StudentInfo findByStudentNumber(String studentnumber);
    StudentInfo findByUniqueWithOutDelete(StudentInfo studentInfo); // 查找唯一对象 不排除已经删除的对象
    StudentInfo findById(String id);
    List<StudentInfo> findByParameterWOP(Map map);

    List<StudentInfo> findAll();
    List<StudentInfo> findByParameter(Map map);

}
