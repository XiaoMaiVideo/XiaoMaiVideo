/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/24
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

    List<Movie> getAll(int page, int total);

    List<Movie> getAllByCategoriesLike(int page, int total,String categories);

    List<Movie> getAllByDescriptionLike(int page, int total,String description);

    List<Movie> getAllByLocation(int page, int total, String location);
}
