package cn.youthol.trainingmanagementsystem.controller;

import cn.youthol.trainingmanagementsystem.entity.Result;
import cn.youthol.trainingmanagementsystem.entity.Sduter;
import cn.youthol.trainingmanagementsystem.entity.Youtholer;
import cn.youthol.trainingmanagementsystem.service.YoutholerService;
import cn.youthol.trainingmanagementsystem.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/youtholer")
public class YoutholerController {

    @Autowired
    private YoutholerService youtholerService;

    @GetMapping("/all")
    public Result<List<Youtholer>> getAllYoutholers() {
        return Result.success(youtholerService.getAllYoutholers());
    }

    @PostMapping("/search")
    public Result<List<Youtholer>> serach(@RequestBody Youtholer youtholer) {
        String sdut_id = youtholer.getSdut_id();
        String name = youtholer.getName();
        String department = youtholer.getDepartment();
        List<Youtholer> res = youtholerService.getAllYoutholers();
        ;
        if (name != "") {
            res.clear();
            res.add(youtholerService.getYoutholerByName(name));
        } else {
            if (department != "") {
                res = youtholerService.getYoutholerByDepartment(department);
            }
        }
        return Result.success(res);
    }

    @PutMapping("/update")
    public Result updateYoutholer(@RequestBody Youtholer youtholer) {
        youtholerService.updateYoutholer(youtholer);
        return Result.success();
    }

    @PostMapping("/create")
    public Result createYoutholer(@RequestBody Youtholer youtholer) {
        youtholerService.createYoutholer(youtholer);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result deleteYoutholer(@RequestBody List<String> sdutIds) {
        sdutIds.forEach(sdutId -> youtholerService.deleteYoutholer(sdutId));
        return Result.success();
    }

    @GetMapping("/youtholerInfo")
    public Result<Youtholer> youtholerInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String sdut_id = (String) map.get("sdut_id");
        Youtholer youtholer = youtholerService.getYoutholerById(sdut_id);
        return Result.success(youtholer);
    }
}
