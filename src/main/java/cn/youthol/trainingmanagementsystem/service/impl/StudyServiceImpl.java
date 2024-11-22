package cn.youthol.trainingmanagementsystem.service.impl;

import cn.youthol.trainingmanagementsystem.entity.Study;
import cn.youthol.trainingmanagementsystem.mapper.StudyMapper;
import cn.youthol.trainingmanagementsystem.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    private StudyMapper studyMapper;

    @Override
    public List<Study> getStudies() {
        List<Study> studies = studyMapper.findAll();
        return studies;
    }

    @Override
    public int addStudy(Study study) {
        return studyMapper.insertStudy(study);
    }

    @Override
    public int updateStudy(Study study) {
        return studyMapper.updateStudy(study);
    }

    @Override
    public int deleteStudy(Integer id) {
        return studyMapper.deleteById(id);
    }
}
