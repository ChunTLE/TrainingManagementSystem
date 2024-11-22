package cn.youthol.trainingmanagementsystem.mapper;

import cn.youthol.trainingmanagementsystem.entity.Sduter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SduterMapper {
    List<Sduter> findAll();
    Sduter findById(String sdut_id);
    void insert(Sduter sduter);
    // 更新
    void update(Sduter sduter);
    // 更新密码
    void updatePwd(String md5String, String sdut_id);
}
