/**
 * Author: 张俊杰，叶俊豪
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */



package com.edu.whu.xiaomaivideo_backend.controller;

import com.edu.whu.xiaomaivideo_backend.model.AjaxResponse;
import com.edu.whu.xiaomaivideo_backend.model.Movie;
import com.edu.whu.xiaomaivideo_backend.service.MovieRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;

@Controller
@RequestMapping("/rest")
public class MovieController {
    @Resource(name="movieRestJPAServiceImpl")
    MovieRestService movieRestService;

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

    @GetMapping("/getMoviesByLocation")
    public @ResponseBody AjaxResponse getMoviesByLocation(@RequestParam int page, @RequestParam int total,@RequestParam String location) {
        return AjaxResponse.success(movieRestService.getAllByLocation(page, total, location));
    }
}
