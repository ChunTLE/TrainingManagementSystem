package cn.youthol.trainingmanagementsystem.service;

import cn.youthol.trainingmanagementsystem.entity.Study;

import java.util.List;

public interface StudyService {
    List<Study> getStudies();
    int addStudy(Study study);
    int updateStudy(Study study);
    int deleteStudy(Integer id);
}
