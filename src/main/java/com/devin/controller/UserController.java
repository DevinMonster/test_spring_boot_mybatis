package com.devin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dto.APIResult;
import com.devin.dto.ResultGenerator;
import com.devin.entity.User;
import com.devin.entity.request.UserRequest;
import com.devin.service.IUserService;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author devin
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
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
    public APIResult edit(@RequestBody UserRequest request) {
        userService.updateById(new User(request));
        return ResultGenerator.genSuccess();
    }

    @PostMapping("/login")
    public APIResult login(@RequestBody UserRequest request) {
        User user = userService.login(request);
        if (user != null) return ResultGenerator.genSuccess(user);
        return ResultGenerator.genFailed("用户名或密码错误");
    }

    @PostMapping("/register")
    public APIResult register(@RequestBody UserRequest request) {
        if (userService.registerUser(request)) return ResultGenerator.genSuccess(request);
        return ResultGenerator.genFailed("用户注册失败");
    }

    @PostMapping("/page")
    public APIResult<Page<User> > page(@RequestBody UserRequest request) {
        return ResultGenerator.genSuccess(userService.pageMine(request));
    }

    /*public static void main(String[] args) {
        String accessKey = "evzPrTT1qVnC1WIcOAeJ763Ar9teglDAJVavCrCQ";
        String secretKey = "nUECECMhOHxp5ZZHiKCg50Yezn4WbZg_EtkhmBLC";
        String bucket = "haoziweizhi1111";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);

    }*/

}
