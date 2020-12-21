package com.devin.controller;

import com.alibaba.fastjson.JSON;
import com.devin.dto.APIResult;
import com.devin.dto.ResultGenerator;
import com.devin.entity.request.UserRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/redis")
@SuppressWarnings("all")
public class RedisController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 向redis中新增数据
     * @param userRequest 用户
     * @return 统一成功
     */
    @PostMapping("/add")
    public APIResult add(@RequestBody UserRequest userRequest) {
        redisTemplate.opsForValue().set("user", JSON.toJSONString(userRequest));
        String user = redisTemplate.opsForValue().get("user");
        System.out.println("user = " + user);
        return ResultGenerator.genSuccess(user);
    }
}
