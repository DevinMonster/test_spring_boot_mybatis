package com.devin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.entity.User;
import com.devin.entity.request.UserRequest;
import com.devin.enums.ApiEnum;
import com.devin.exception.GlobalException;
import com.devin.mapper.UserMapper;
import com.devin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.utils.JWTUtil;
import com.devin.utils.MD5Utils;
import com.devin.utils.Utils;
import com.google.common.collect.Collections2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author devin
 * @since 2020-12-15
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean add(UserRequest user) {
        User user1 = userMapper.selectByName(user.getUsername());
        System.out.println("user1 = " + user1);
        if (user1 == null) {
            userMapper.insertUser(new User(user));
            return true;
        }
        return false;
    }

    @Override
    public User login(UserRequest request) {
        String code = (String) redisTemplate.opsForValue().get(request.getKey());
        // 验证码校验不通过
        if (code == null || !code.equalsIgnoreCase(request.getVerifyCode()))
            throw new GlobalException(ApiEnum.VERIFYCODE_ERROR);
        // 删除验证码
        redisTemplate.delete(request.getKey());
        // 根据用户名查询用户
        User user = userMapper.selectByName(request.getUsername());
        if (!Utils.userCheck(user, request)) throw new GlobalException(ApiEnum.USERNAME_PASSWORD_ERROR);
        // 保存用户
        return user;
    }

    @Override
    public Page<User> pageMine(UserRequest request) {
        Page<User> page = new Page<>(request.getPage(), request.getSize());
        return userMapper.selectPageByRequest(page, request);
    }

    @Override
    public User registerUser(UserRequest user) {
        // 查询用户名是否存在
        User select = userMapper.selectByName(user.getUsername());
        if (select != null) throw new GlobalException(ApiEnum.USERNAME_REGISTED);
        // 确认密码
        if (!user.getPassword().equals(user.getCheckPassword())) throw new GlobalException(ApiEnum.PASSWORD_NOT_MATCH);
        // md5加密
        User add = new User(user);
        add.setPassword(MD5Utils.encode(add.getUsername()));
        return add;
    }
}
