package com.edu.whu.xiaomaivideo_backend.controller;



import com.edu.whu.xiaomaivideo_backend.model.AjaxResponse;
import com.edu.whu.xiaomaivideo_backend.model.User;
import com.edu.whu.xiaomaivideo_backend.service.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
@RequestMapping("/rest")
public class UserController {

    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @PostMapping("/user")
    public @ResponseBody
    AjaxResponse saveUser(@RequestBody User user) {
        user.setUserId(null);
        User user1= userRestService.saveUser(user);
        return AjaxResponse.success(user1);
    }

    @DeleteMapping("/user/{id}")
    public @ResponseBody
    AjaxResponse deleteUser(@PathVariable Long id) {

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
        return AjaxResponse.success(userRestService.getUser(username));
    }

    @GetMapping( "/user/{id}")
    public @ResponseBody AjaxResponse getUserById(@PathVariable Long id) {
        return AjaxResponse.success(userRestService.getUserById(id));
    }
}
