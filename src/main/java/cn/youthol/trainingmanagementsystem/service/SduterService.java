package cn.youthol.trainingmanagementsystem.service;

import cn.youthol.trainingmanagementsystem.entity.Sduter;

import java.util.List;

public interface SduterService {
    List<Sduter> getSduters();
    Sduter findSduterById(String sdut_id);
    void register(String sdut_id, String password, String name);
    String login(Sduter sduter);
    // 更新
    void update(Sduter sduter);
    // 更新密码
    void updatePwd(String newPwd);
}
