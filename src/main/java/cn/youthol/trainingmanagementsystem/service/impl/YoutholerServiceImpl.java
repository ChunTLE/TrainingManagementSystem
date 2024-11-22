package cn.youthol.trainingmanagementsystem.service.impl;

import cn.youthol.trainingmanagementsystem.entity.Youtholer;
import cn.youthol.trainingmanagementsystem.mapper.YoutholerMapper;
import cn.youthol.trainingmanagementsystem.service.YoutholerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutholerServiceImpl implements YoutholerService {

    @Autowired
    private YoutholerMapper youtholerMapper;

    @Override
    public List<Youtholer> getAllYoutholers() {
        return youtholerMapper.findAll();
    }

    @Override
    public Youtholer getYoutholerById(String sdut_id) {
        return youtholerMapper.findById(sdut_id);
    }

    @Override
    public Youtholer getYoutholerByName(String name) {
        return youtholerMapper.findByName(name);
    }

    @Override
    public List<Youtholer> getYoutholerByDepartment(String department) {
        return youtholerMapper.findByDepartment(department);
    }

    @Override
    public void createYoutholer(Youtholer youtholer) {
        youtholerMapper.insert(youtholer);
    }

    @Override
    public void updateYoutholer(Youtholer youtholer) {
        youtholerMapper.update(youtholer);
    }

    @Override
    public void deleteYoutholer(String sdut_id) {
        youtholerMapper.deleteById(sdut_id);
    }
}
