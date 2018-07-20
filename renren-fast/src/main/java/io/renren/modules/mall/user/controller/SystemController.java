package io.renren.modules.mall.user.controller;

import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.UserService;
import io.renren.modules.mall.entity.ResponseBody;
import io.renren.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GavinCee on 2018/7/4.
 */
@RestController
@RequestMapping("/system")
@Api("系统操作接口")
public class SystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public ResponseBody login(String username, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        UserEntity res = userService.queryByUsername(username);
        if(res == null) {
            return new ResponseBody("用户不存在", false, "用户不存在");
        } else {
            if(!res.getPassword().equals(password)) {
                return new ResponseBody("密码错误", false, "密码错误");
            }
        }
        R r = sysUserTokenService.createToken(res.getUserId());
        return  new ResponseBody("登录成功", true, r);
    }

    /**
     * 注册接口
     */
    @PostMapping("register")
    @ApiOperation("注册")
    public ResponseBody register(String username, String password, String mobile) {
        userService.addUser(username, password, mobile);
        return new ResponseBody("用户注册成功", true, "用户注册成功");
    }

    /**
     * 注册接口
     */
    @PostMapping("register")
    @ApiOperation("注册")
    public ResponseBody register(String username, String password) {
        userService.addUser(username, password, null);
        return new ResponseBody("用户注册成功", true, "用户注册成功");
    }

    /**
     * 发送短信验证码
     */
    @GetMapping("sendCode")
    @ApiOperation("发送短信验证码")
    public ResponseBody sendCode() {
        //TODO
        return null;
    }

    /**
     * 判断电话号码是否存在
     */
    @GetMapping("isPhoneExist")
    @ApiOperation("注册")
    public ResponseBody isPhoneExist(String phone) {
        UserEntity userEntity =  userService.queryByMobile(phone);
        if(userEntity != null) {
            return new ResponseBody("号码已存在", false, phone);
        } else {
            return new ResponseBody("号码可使用", true, phone);
        }
    }
}
