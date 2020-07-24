/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/8
 * Update Time: 2020/7/24
 */


package com.edu.whu.xiaomaivideo_backend.controller;



import com.edu.whu.xiaomaivideo_backend.model.AjaxResponse;
import com.edu.whu.xiaomaivideo_backend.model.Movie;
import com.edu.whu.xiaomaivideo_backend.model.Share;
import com.edu.whu.xiaomaivideo_backend.model.User;
import com.edu.whu.xiaomaivideo_backend.service.MovieRestService;
import com.edu.whu.xiaomaivideo_backend.service.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.*;


@Controller
@RequestMapping("/rest")
public class UserController {

    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @Resource(name="movieRestJPAServiceImpl")
    MovieRestService movieRestService;

    //添加用户
    @PostMapping("/user")
    public @ResponseBody AjaxResponse saveUser(@RequestBody User user) {
        user.setUserId(null);
        User user1 = userRestService.getUser(user.getUsername());
        // 用户名已存在
        if (user1 != null) {
            return new AjaxResponse(4, "The user already exists", false);
        }
        try {
            // 给一个默认的昵称和头像
            user.setNickname(user.getUsername());
            user.setAvatar("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1093847288,3038136586&fm=26&gp=0.jpg");
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
        List<Movie> movies=user1.getMovies();
        for (Movie movie:user.getMovies()) {
            // movie.setPublishTime(new Date());
            movie.setPublisher(user1);
        }
        movies.addAll(user.getMovies());
        user1.setMovies(movies);
        try {
            userRestService.saveUser(user1);
            User user2 = userRestService.getUserById(user1.getUserId());
            movies = user2.getMovies();
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie m1, Movie m2) {
                    return m2.getPublishTime().compareTo(m1.getPublishTime());
                }
            });
            return AjaxResponse.success(movies.get(0).getMovieId());
        }
        catch (Exception e) {
            return AjaxResponse.failure();
        }
    }

    @PostMapping("/shareMovies")
    public @ResponseBody AjaxResponse saveShareMovies(@RequestBody User user) {
        User user1 = userRestService.getUser(user.getUsername());
        List<Share> shares=user1.getShares();
        for (Share share:user.getShares()){
            share.setMovie(movieRestService.getMovieById(share.getMovie().getMovieId()));
            share.setShareDate(new Date());
        }
        shares.addAll(user.getShares());
        user1.setShares(shares);
        try {
            userRestService.saveUser(user1);
            return AjaxResponse.success(user1);
        }
        catch (Exception e) {
            return AjaxResponse.failure();
        }
    }





//    //用户点赞视频，可以同时多个，外层用户需要用户名
//    //内曾视频需要用户点赞视频的movieId
//    @PostMapping("/userLike")
//    public @ResponseBody AjaxResponse saveUserLike(@RequestBody User user) {
//        User user1 = userRestService.getUser(user.getUsername());
//        List<Movie> movies=user1.getLikeMovies();
//
//        for (Movie movie:user.getLikeMovies()) {
//            Movie movie1=movieRestService.getMovieById(movie.getMovieId());
//            movies.add(movie1);
//        }
//        user1.setLikeMovies(movies);
//        try {
//            userRestService.saveUser(user1);
//            return AjaxResponse.success(user1);
//        }
//        catch (Exception e) {
//            return AjaxResponse.failure();
//        }
//    }

    //删除用户
    @DeleteMapping("/user/{id}")
    public @ResponseBody AjaxResponse deleteUser(@PathVariable Long id) {
        userRestService.deleteUser(id);
        return AjaxResponse.success(id);
    }

    @PutMapping("/user")
    public @ResponseBody AjaxResponse updateUser(@RequestBody User user) {
        // 传上来密码是空的，先填个密码
        User user1 = userRestService.getUser(user.getUsername());
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            user.setPassword(user1.getPassword());
        }
        // 也不知道为什么update以后就出问题了，先留着
        user.setMovies(user1.getMovies());
        user.setShares(user1.getShares());
        user.setComments(user1.getComments());
        user.setLikeMovies(user1.getLikeMovies());
        user.setFollowers(user1.getFollowers());
        user.setFollowing(user.getFollowing());
        user.setReceivemsgs(user.getReceivemsgs());
        user.setSendmsgs(user.getSendmsgs());
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
    public @ResponseBody AjaxResponse getUserById(@PathVariable Long id, @RequestParam Long userId) {
        User user = userRestService.getUserById(id);
        if (userId == 0) {
            return AjaxResponse.success(user);
        }
        User user1=userRestService.getUserById(userId);
        if (user.getFollowers().contains(user1)){
            user.setIsFollow(true);
        }else {
            user.setIsFollow(false);
        }
        List<Movie> publishMovies=user.getMovies();
        for (Movie movie:publishMovies){
            if (user1.getLikeMovies().contains(movie)){
                movie.setIsLike(true);
            }else {
                movie.setIsLike(false);
            }
        }
        user.setMovies(publishMovies);
        List<Movie> likeMovies=user.getLikeMovies();
        for (Movie movie:likeMovies){
            if (user1.getLikeMovies().contains(movie)){
                movie.setIsLike(true);
            }else {
                movie.setIsLike(false);
            }
        }
        user.setLikeMovies(likeMovies);
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

    @PostMapping(value = "/user/getSimpleUserInfo")
    public @ResponseBody AjaxResponse getSimpleUserInfo(@RequestBody Long[] userIds) {
        List<User> users = new ArrayList<>();
        for (Long userId: userIds) {
            User user1 = userRestService.getUserById(userId);
            User user2 = new User();
            user2.setNickname(user1.getNickname());
            user2.setAvatar(user1.getAvatar());
            user2.setDescription(user1.getDescription());
            users.add(user2);
        }
        return AjaxResponse.success(users);
    }

    @GetMapping("/getUsersByNicknameLike")
    public @ResponseBody AjaxResponse getUsersByUsernameLike(@RequestParam int page, @RequestParam int total, @RequestParam String nickname) {
        return AjaxResponse.success(userRestService.getAllByNicknameLike(page, total,"%"+nickname+"%"));
    }
}
