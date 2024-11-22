package cn.youthol.trainingmanagementsystem.controller;

import cn.youthol.trainingmanagementsystem.entity.Result;
import cn.youthol.trainingmanagementsystem.entity.Study;
import cn.youthol.trainingmanagementsystem.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study")
public class StudyController {

    @Autowired
    private StudyService studyService;

    @GetMapping("/all")
    public Result<List<Study>> getStudies() {
        return Result.success(studyService.getStudies());
    }

    @PostMapping("/add")
    public Result addStudy(@RequestBody Study study) {
        studyService.addStudy(study);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateStudy(@RequestBody Study study) {
        studyService.updateStudy(study);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result deleteStudy(@RequestBody Study Study) {
        studyService.deleteStudy(Study.getId());
        return Result.success();
    }
}
