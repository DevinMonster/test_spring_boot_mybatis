package com.devin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.devin.entity.request.UserRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author devin
 * @since 2020-12-15
 */
public interface IUserService extends IService<User> {

    boolean add(UserRequest user);

    User login(UserRequest request);

    Page<User> pageMine(UserRequest userPage);

    User registerUser(UserRequest user);
}
