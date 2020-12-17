package com.devin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.entity.User;
import com.devin.entity.request.UserRequest;
import com.devin.mapper.UserMapper;
import com.devin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.utils.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author devin
 * @since 2020-12-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

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
        User user = userMapper.selectByName(request.getUsername());
        if (Utils.userCheck(user, request)) return user;
        return null;
    }

    @Override
    public Page<User> pageMine(UserRequest request) {
        Page<User> page = new Page<>(request.getPage(), request.getSize());
        return userMapper.selectPageByRequest(page, request);
    }

    @Override
    public boolean registerUser(UserRequest user) {
        if (Utils.userLeagle(user)) {
            Integer insert = userMapper.insert(new User(user));
            return Utils.retBool(insert);
        }
        return false;
    }
}
