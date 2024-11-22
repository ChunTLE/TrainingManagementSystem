package cn.youthol.trainingmanagementsystem.service;

import cn.youthol.trainingmanagementsystem.entity.Youtholer;

import java.util.List;

public interface YoutholerService {
    List<Youtholer> getAllYoutholers();
    Youtholer getYoutholerById(String sdut_id);
    Youtholer getYoutholerByName(String name);
    List<Youtholer> getYoutholerByDepartment(String department);
    void createYoutholer(Youtholer youtholer);
    void updateYoutholer(Youtholer youtholer);
    void deleteYoutholer(String sdut_id);
}
