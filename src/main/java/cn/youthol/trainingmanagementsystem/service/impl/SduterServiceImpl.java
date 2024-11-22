package cn.youthol.trainingmanagementsystem.service.impl;

import cn.youthol.trainingmanagementsystem.entity.Sduter;
import cn.youthol.trainingmanagementsystem.mapper.SduterMapper;
import cn.youthol.trainingmanagementsystem.service.SduterService;
import cn.youthol.trainingmanagementsystem.utils.Md5Util;
import cn.youthol.trainingmanagementsystem.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SduterServiceImpl implements SduterService {
    @Autowired
    private SduterMapper sduterMapper;

    @Override
    public List<Sduter> getSduters() {
        List<Sduter> sduters = sduterMapper.findAll();
        return sduters;
    }

    @Override
    public Sduter findSduterById(String sdut_id) {
        return sduterMapper.findById(sdut_id);
    }

    @Override
    public void register(String sdut_id, String password, String name) {
        Sduter sduter = new Sduter();
        String md5String = Md5Util.getMD5String(password);
        sduter.setSdut_id(sdut_id);
        sduter.setName(name);
        sduter.setPassword(md5String);
        sduterMapper.insert(sduter);
    }

    @Override
    public String login(Sduter sduter) {
        String sdut_id = sduter.getSdut_id();
        String password = sduter.getPassword();
        if(sdut_id == null || "".equals(sdut_id) || password == null || "".equals(password)) {
            return "用户名或密码不能为空";
        }
        if(findSduterById(sdut_id) == null){
            return "用户不存在";
        }
        if(!password.equals(findSduterById(sdut_id).getPassword())) {
            return "密码错误";
        }
        return "success";
    }

    @Override
    public void update(Sduter sduter) {
        sduterMapper.update(sduter);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String sdut_id = (String) map.get("sdut_id");
        sduterMapper.updatePwd(Md5Util.getMD5String(newPwd), sdut_id);
    }
}
