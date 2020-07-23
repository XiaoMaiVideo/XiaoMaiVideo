/**
 * Author: 张俊杰，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/22
 */



package com.edu.whu.xiaomaivideo_backend.controller;

import com.edu.whu.xiaomaivideo_backend.model.AjaxResponse;
import com.edu.whu.xiaomaivideo_backend.model.Message;
import com.edu.whu.xiaomaivideo_backend.model.Movie;
import com.edu.whu.xiaomaivideo_backend.model.User;
import com.edu.whu.xiaomaivideo_backend.service.MovieRestService;
import com.edu.whu.xiaomaivideo_backend.service.UserRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class MovieController {
    @Resource(name="movieRestJPAServiceImpl")
    MovieRestService movieRestService;

    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @DeleteMapping("/movie/{id}")
    public @ResponseBody AjaxResponse deleteMovie(@PathVariable Long id) {
        movieRestService.deleteMovie(id);
        return AjaxResponse.success(id);
    }

    @GetMapping( "/movie/{id}")
    public @ResponseBody AjaxResponse getMovieById(@PathVariable Long id) {
        Movie movie = movieRestService.getMovieById(id);
        return AjaxResponse.success(movie);
    }

    @GetMapping("/getMovies")
    public @ResponseBody AjaxResponse getMovies(@RequestParam int page, @RequestParam int total) {
        return AjaxResponse.success(movieRestService.getAll(page, total));
    }

    @GetMapping("/getMoviesByCategoriesLike")
    public @ResponseBody AjaxResponse getMoviesByCategoriesLike(@RequestParam int page, @RequestParam int total,@RequestParam String categories) {
        return AjaxResponse.success(movieRestService.getAllByCategoriesLike(page, total,"%"+categories+"%"));
    }

    @GetMapping("/getMoviesByDescriptionLike")
    public @ResponseBody AjaxResponse getMoviesByDescriptionLike(@RequestParam int page, @RequestParam int total,@RequestParam String description) {
        return AjaxResponse.success(movieRestService.getAllByDescriptionLike(page, total,"%"+description+"%"));
    }

    @GetMapping("/getMoviesByLocation")
    public @ResponseBody AjaxResponse getMoviesByLocation(@RequestParam int page, @RequestParam int total,@RequestParam String location) {
        return AjaxResponse.success(movieRestService.getAllByLocation(page, total, location));
    }

    //获取关注粉丝的视频
    @GetMapping("/getRelatedMoviesById")
    public @ResponseBody AjaxResponse getRelatedMoviesById(@RequestParam Long userId) {
        User user=userRestService.getUserById(userId);
        List<Movie> movies=new ArrayList<>();
        for (User following:user.getFollowing()){
            movies.addAll(following.getMovies());
        }
        for (User follower:user.getFollowers()){
            movies.addAll(follower.getMovies());
        }

        Collections.sort(movies, new Comparator<Movie>() {

            @Override
            public int compare(Movie m1, Movie m2) {
                return m2.getPublishTime().compareTo(m1.getPublishTime());
            }
        });
        return AjaxResponse.success(movies);
    }
}
