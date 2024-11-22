package cn.youthol.trainingmanagementsystem.controller;

import cn.youthol.trainingmanagementsystem.entity.Result;
import cn.youthol.trainingmanagementsystem.entity.Sduter;
import cn.youthol.trainingmanagementsystem.service.SduterService;
import cn.youthol.trainingmanagementsystem.utils.JwtUtil;
import cn.youthol.trainingmanagementsystem.utils.Md5Util;
import cn.youthol.trainingmanagementsystem.utils.ThreadLocalUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class SduterController {
    @Autowired
    private SduterService sduterService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/register")
    public Result register(@RequestBody Sduter sduter) {
        String sdut_id = sduter.getSdut_id();
        String password = sduter.getPassword();
        String name = sduter.getName();
        Sduter existingSduter = sduterService.findSduterById(sdut_id);
        if (existingSduter == null) {
            // 可以注册
            sduterService.register(sdut_id, password, name);
            return Result.success();
        } else {
            // 不能注册
            return Result.error("用户已存在");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody Sduter sduter) throws JsonProcessingException {
        // 根据用户名查询用户
        String sdut_id = sduter.getSdut_id();
        String password = sduter.getPassword();
        Sduter existingSduter = sduterService.findSduterById(sdut_id);
        // 判断用户是否存在
        if (existingSduter == null) {
            return Result.error("用户名错误");
        }
        // 判断密码是否正确
        if (Md5Util.getMD5String(password).equals(existingSduter.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("sdut_id", sdut_id);
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<Sduter> userInfo() {
        // 根据用户名查询用户
        Map<String, Object> map = ThreadLocalUtil.get();
        String sdut_id = (String) map.get("sdut_id");
        Sduter sduter = sduterService.findSduterById(sdut_id);
        sduter.setSdut_id(sdut_id);
        return Result.success(sduter);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Sduter sduter) {
        sduterService.update(sduter);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String rePwd = params.get("rePwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("密码为空");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String sdut_id = (String) map.get("sdut_id");
        Sduter loginsduter = sduterService.findSduterById(sdut_id);
        if (!loginsduter.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次密码不一致");
        }

        sduterService.updatePwd(newPwd);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
