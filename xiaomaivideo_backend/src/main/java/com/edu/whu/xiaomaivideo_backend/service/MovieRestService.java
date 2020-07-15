/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */



package com.edu.whu.xiaomaivideo_backend.service;


import com.edu.whu.xiaomaivideo_backend.model.Movie;

import java.util.List;

public interface MovieRestService {
    Movie saveMovie(Movie movie);

    void deleteMovie(Long id);

    void updateMovie(Movie movie);

    Movie getMovieById(Long id);

    List<Movie> getAll();
}
