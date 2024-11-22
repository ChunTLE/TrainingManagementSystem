package cn.youthol.trainingmanagementsystem.mapper;

import cn.youthol.trainingmanagementsystem.entity.Youtholer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface YoutholerMapper {
    // 查询所有数据
    List<Youtholer> findAll();

    // 根据 sdut_id 查询
    Youtholer findById(@Param("sdut_id") String sdut_id);

    // 根据 name 查询
    Youtholer findByName(@Param("name") String name);

    // 根据 department 查询
    List<Youtholer> findByDepartment(@Param("department") String department);

    // 插入数据
    void insert(Youtholer youtholer);

    // 更新数据
    void update(Youtholer youtholer);

    // 删除数据
    void deleteById(@Param("sdut_id") String sdut_id);
}
