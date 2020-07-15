/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/8
 * Update Time: 2020/7/15
 */


package com.edu.whu.xiaomaivideo_backend.controller;



import com.edu.whu.xiaomaivideo_backend.model.AjaxResponse;
import com.edu.whu.xiaomaivideo_backend.model.Movie;
import com.edu.whu.xiaomaivideo_backend.model.User;
import com.edu.whu.xiaomaivideo_backend.service.MovieRestService;
import com.edu.whu.xiaomaivideo_backend.service.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/rest")
public class UserController {

    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @Resource(name="movieRestJPAServiceImpl")
    MovieRestService movieRestService;

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
            //@JsonIgnoreProperties(value="password", allowSetters=true)
            //不会返回密码
            return AjaxResponse.success(user);
        }
        catch (Exception e) {
            return AjaxResponse.failure();
        }
    }

    //用户发布视频，可同时多个，外层用户需要有用户名，其他可省略，
    //内层视频数组不需要userId,likers,publisher,但需要有一定的描述属性
    @PostMapping("/userMovies")
    public @ResponseBody AjaxResponse saveUserMovies(@RequestBody User user) {
        User user1 = userRestService.getUser(user.getUsername());
        Set<Movie> movies=user1.getMovies();
        for (Movie movie:user.getMovies()) {
            movie.setPublisher(user1);
        }
        movies.addAll(user.getMovies());
        user1.setMovies(movies);
        try {
            userRestService.saveUser(user1);
            return AjaxResponse.success(user1);
        }
        catch (Exception e) {
            return AjaxResponse.failure();
        }
    }


    //用户点赞视频，可以同时多个，外层用户需要用户名
    //内曾视频需要用户点赞视频的movieId
    @PostMapping("/userLike")
    public @ResponseBody AjaxResponse saveUserLike(@RequestBody User user) {
        User user1 = userRestService.getUser(user.getUsername());
        List<Movie> movies=user1.getLikeMovies();

        for (Movie movie:user.getLikeMovies()){
            Movie movie1=movieRestService.getMovieById(movie.getMovieId());
            movies.add(movie1);
        }
        user1.setLikeMovies(movies);
        try {
            userRestService.saveUser(user1);
            return AjaxResponse.success(user1);
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
        // 传上来密码是空的，先填个密码
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            User user1 = userRestService.getUserById(user.getUserId());
            user.setPassword(user1.getPassword());
        }
        userRestService.updateUser(user);
        return AjaxResponse.success();
    }

    //获取一个user可以获取到一个用户需要的全部信息（应该）
    @GetMapping("/user/get")
    public @ResponseBody AjaxResponse getUserByUsername(@RequestParam String username) {
        User user = userRestService.getUser(username);
        return AjaxResponse.success(user);
    }

    //获取一个user可以获取到一个用户需要的全部信息（应该）
    @GetMapping("/user/{id}")
    public @ResponseBody AjaxResponse getUserById(@PathVariable Long id) {
        User user = userRestService.getUserById(id);
        return AjaxResponse.success(user);
    }

    @GetMapping("/getAllUser")
    public @ResponseBody AjaxResponse getAllUser() {
        return AjaxResponse.success(userRestService.getAllUser());
}

    @PostMapping(value = "/user/verify")
    public @ResponseBody AjaxResponse verifyUser(@RequestBody User user) {
        User user1 = userRestService.getUser(user.getUsername());
        if (user1 == null) {
            // 没有注册
            return new AjaxResponse(1, "The user not exists", false);
        }
        else if (!user1.getPassword().equals(user.getPassword())) {
            // 密码不正确
            return new AjaxResponse(2, "Incorrect Password", false);
        }
        else {
            // 登录成功
            return AjaxResponse.success(user1);
        }
    }
}
