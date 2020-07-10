package com.edu.whu.xiaomaivideo_backend.controller;



import com.edu.whu.xiaomaivideo_backend.model.AjaxResponse;
import com.edu.whu.xiaomaivideo_backend.model.User;
import com.edu.whu.xiaomaivideo_backend.service.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;


@Controller
@RequestMapping("/rest")
public class UserController {

    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @PostMapping("/user")
    public @ResponseBody AjaxResponse saveUser(@RequestBody User user) {
        user.setUserId(null);
        User user1 = userRestService.getUser(user.getUsername());
        // 用户名已存在
        if (user1 != null) {
            return new AjaxResponse(4, "The user already exists", false);
        }
        try {
            userRestService.saveUser(user);
            return AjaxResponse.success();
        }
        catch (Exception e) {
            return AjaxResponse.failure();
        }
    }

    @DeleteMapping("/user/{id}")
    public @ResponseBody AjaxResponse deleteUser(@PathVariable Long id) {
        userRestService.deleteUser(id);
        return AjaxResponse.success(id);
    }

    @PutMapping("/user")
    public @ResponseBody AjaxResponse updateUser(@RequestBody User user) {

        userRestService.updateUser(user);
        return AjaxResponse.success(user);
    }

    @GetMapping( "/user/get")
    public @ResponseBody AjaxResponse getUserByUsername(@RequestParam String username) {
        User user = userRestService.getUser(username);
        user.setPassword("");
        return AjaxResponse.success(user);
    }

    @GetMapping( "/user/{id}")
    public @ResponseBody AjaxResponse getUserById(@PathVariable Long id) {
        User user = userRestService.getUserById(id);
        user.setPassword("");
        return AjaxResponse.success(user);
    }

    @PostMapping(value = "/user/verify")
    public @ResponseBody AjaxResponse verifyUser(@RequestBody Map map) {
        String username = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        User user = userRestService.getUser(username);
        if (user == null) {
            // 没有注册
            return new AjaxResponse(1, "The user not exists", false);
        }
        else if (!password.equals(user.getPassword())) {
            // 密码不正确
            return new AjaxResponse(2, "Incorrect Password", false);
        }
        else {
            // 登录成功
            user.setPassword("");
            return AjaxResponse.success(user);
        }
    }
}
