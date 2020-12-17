package com.devin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devin.entity.request.UserRequest;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author devin
 * @since 2020-12-15
 */
public interface UserMapper extends BaseMapper<User> {

    void insertUser(User user);

    User selectByName(String username);

    Page<User> selectPageByRequest(@Param("page") Page<User> page, @Param("request") UserRequest request);
}
