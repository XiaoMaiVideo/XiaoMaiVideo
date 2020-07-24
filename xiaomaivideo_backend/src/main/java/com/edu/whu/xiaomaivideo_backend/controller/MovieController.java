/**
 * Author: 张俊杰，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/24
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
    public @ResponseBody AjaxResponse getMovieById(@PathVariable Long id, @RequestParam Long userId) {
        Movie movie = movieRestService.getMovieById(id);
        if (userId == 0) {
            return AjaxResponse.success(movie);
        }
        User user = userRestService.getUserById(userId);
        movie.setIsLike(user.getLikeMovies().contains(movie));
        return AjaxResponse.success(movie);
    }

    @GetMapping("/getMovies")
    public @ResponseBody AjaxResponse getMovies(@RequestParam int page, @RequestParam int total,
                                                @RequestParam long userId) {

        List<Movie> movies = movieRestService.getAll(page, total);
        if (userId == 0) {
            return AjaxResponse.success(movies);
        }
        User user = userRestService.getUserById(userId);

        for (Movie movie:movies) {
            if (user.getLikeMovies().contains(movie)){
                movie.setIsLike(true);
            }else {
                movie.setIsLike(false);
            }
        }
        return AjaxResponse.success(movies);
    }

    @GetMapping("/getMoviesByCategoriesLike")
    public @ResponseBody AjaxResponse getMoviesByCategoriesLike(@RequestParam int page, @RequestParam int total,
                                                                @RequestParam String categories,@RequestParam Long userId) {
        List<Movie> movies=movieRestService.getAllByCategoriesLike(page, total,"%"+categories+"%");
        if (userId == 0) {
            return AjaxResponse.success(movies);
        }

        User user=userRestService.getUserById(userId);
        for (Movie movie:movies) {
            if (user.getLikeMovies().contains(movie)){
                movie.setIsLike(true);
            }else {
                movie.setIsLike(false);
            }
        }
        return AjaxResponse.success(movies);
    }

    @GetMapping("/getMoviesByDescriptionLike")
    public @ResponseBody AjaxResponse getMoviesByDescriptionLike(@RequestParam int page, @RequestParam int total,
                                                                 @RequestParam String description,@RequestParam Long userId) {
        List<Movie> movies=movieRestService.getAllByDescriptionLike(page, total,"%"+description+"%");
        if (userId == 0) {
            return AjaxResponse.success(movies);
        }
        User user=userRestService.getUserById(userId);

        for (Movie movie:movies){
            if (user.getLikeMovies().contains(movie)){
                movie.setIsLike(true);
            }else {
                movie.setIsLike(false);
            }
        }
        return AjaxResponse.success(movies);
    }

    @GetMapping("/getMoviesByLocation")
    public @ResponseBody AjaxResponse getMoviesByLocation(@RequestParam int page, @RequestParam int total,
                                                          @RequestParam String location,@RequestParam Long userId) {
        List<Movie> movies=movieRestService.getAllByLocation(page, total, location);
        if (userId == 0) {
            return AjaxResponse.success(movies);
        }
        User user=userRestService.getUserById(userId);

        for (Movie movie:movies) {
            if (user.getLikeMovies().contains(movie)){
                movie.setIsLike(true);
            }else {
                movie.setIsLike(false);
            }
        }
        return AjaxResponse.success(movies);
    }

    //获取关注粉丝的视频
    @GetMapping("/getRelatedMoviesById")
    public @ResponseBody AjaxResponse getRelatedMoviesById(@RequestParam Long userId) {
        if (userId == 0) {
            return AjaxResponse.success(new ArrayList<Movie>());
        }
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
        for (Movie movie:movies){
            if (user.getLikeMovies().contains(movie)){
                movie.setIsLike(true);
            }else {
                movie.setIsLike(false);
            }
        }
        return AjaxResponse.success(movies);
    }
}
