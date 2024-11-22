package cn.youthol.trainingmanagementsystem.mapper;

import cn.youthol.trainingmanagementsystem.entity.Study;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyMapper {
    List<Study> findAll();

    int insertStudy(Study study);

    int updateStudy(Study study);

    int deleteById(Integer id);

}
