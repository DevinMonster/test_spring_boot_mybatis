package com.devin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dto.APIResult;
import com.devin.dto.ResultGenerator;
import com.devin.entity.User;
import com.devin.entity.request.UserRequest;
import com.devin.enums.ApiEnum;
import com.devin.exception.GlobalException;
import com.devin.service.IUserService;
import com.devin.utils.JWTUtil;
import com.devin.utils.VerifyCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author devin
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/user")
@SuppressWarnings("all")
public class UserController {

    @Resource
    private VerifyCode vef;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private IUserService userService;

    @PostMapping("/add")
    public APIResult add(@RequestBody @Validated UserRequest user) {
        // 使用自带api增加
        // userService.save(user);
        userService.add(user);
        return ResultGenerator.genSuccess();
    }

    @PostMapping("/delete")
    public APIResult delete(@RequestBody Long id) {
        userService.removeById(id);
        return ResultGenerator.genSuccess();
    }

    @GetMapping("/list")
    public APIResult<User> list() {
        List<User> list = userService.list();
        return ResultGenerator.genSuccess(list);
    }

    @PostMapping("/update")
    public APIResult edit(@RequestBody UserRequest request, HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        User user = new User(request);
        user.setId(userId);
        userService.updateById(user);
        return ResultGenerator.genSuccess();
    }

    @PostMapping("/login")
    @CrossOrigin
    public APIResult login(@RequestBody UserRequest request) {
//        System.out.println(request);
        // 这里是通过检查的用户
        User user = userService.login(request);
        String token;
        try {
            // 保存用户
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getId());
            map.put("username", user.getUsername());
            token = JWTUtil.createJWT(user.getId().toString(), map, user.getUsername(), System.currentTimeMillis());
            // 将token放置进redis
            redisTemplate.opsForValue().set(user.getId() + "-token", token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(ApiEnum.FAILED);
        }
        // 返回用户的登陆信息, 也就是token和请求头
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", "Authorization");

        return ResultGenerator.genSuccess(tokenMap);
    }

    @PostMapping("/register")
    @CrossOrigin
    public APIResult register(@RequestBody UserRequest request) {
        User user = userService.registerUser(request);
        if (user != null)
            return ResultGenerator.genSuccess(user);

        return ResultGenerator.genFailed("用户注册失败");
    }

    @PostMapping("/page")
    public APIResult<Page<User>> page(@RequestBody UserRequest request) {
        return ResultGenerator.genSuccess(userService.pageMine(request));
    }

    @GetMapping("/code")
    @CrossOrigin
    /**
     * 这个函数的作用是生成验证码，并将验证码存入session中
     */
    public APIResult getImage(HttpSession httpSession, HttpServletResponse response) throws IOException {
        vef.createCode();
        // 把验证码存到redis里
        String text = vef.getCode();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(uuid, text);

        String encoded = vef.bufferToBase64();
        Map<String, Object> map = new HashMap<>();
        map.put("encoded", encoded);
        map.put("key", uuid);
        return ResultGenerator.genSuccess(map);
    }
}
