/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/22
 */



package com.edu.whu.xiaomaivideo_backend.service;


import com.edu.whu.xiaomaivideo_backend.model.Movie;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieRestService {
    Movie saveMovie(Movie movie);

    void deleteMovie(Long id);

    void updateMovie(Movie movie);

    Movie getMovieById(Long id);

    Page<Movie> getAll(int page, int total);

    Page<Movie> getAllByCategoriesLike(int page, int total,String categories);

    Page<Movie> getAllByDescriptionLike(int page, int total,String description);

    Page<Movie> getAllByLocation(int page, int total, String location);
}
