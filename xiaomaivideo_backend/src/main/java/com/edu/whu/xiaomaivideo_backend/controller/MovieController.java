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

    @PostMapping("/movie")
    public @ResponseBody AjaxResponse saveMovie(@RequestBody Movie movie) {
        movie.setMovieId(null);
        movieRestService.saveMovie(movie);
        // 可能需要进行对类别的处理，但我没想好
        return AjaxResponse.success(movie);
    }

    @PutMapping("/movie")
    public @ResponseBody AjaxResponse updateMovie(@RequestBody Movie movie) {
        movieRestService.updateMovie(movie);
        return AjaxResponse.success(movie);
    }

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
        return AjaxResponse.success(movieRestService.getAll(page,total));
    }
}
