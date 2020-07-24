/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/24
 */


package com.edu.whu.xiaomaivideo_backend.service;

import com.edu.whu.xiaomaivideo_backend.dao.MovieRepository;
import com.edu.whu.xiaomaivideo_backend.dao.UserRepository;
import com.edu.whu.xiaomaivideo_backend.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieRestJPAServiceImpl implements MovieRestService {
    @Resource
    private MovieRepository movieRepository;

    @Override
    public Movie saveMovie(Movie movie) {
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).get();
    }

    @Override
    public List<Movie> getAll(int page, int total) {
        // TODO: 按照发布时间倒序
        Pageable pageable = PageRequest.of(page, total, Sort.by("publishTime").descending());
        return movieRepository.findAll(pageable).getContent();
//        //将查询结果转换为List
//        List<Movie> movieList = moviePage.getContent();
//        return movieList;
    }

    @Override
    public List<Movie> getAllByCategoriesLike(int page, int total,String categories) {
        Pageable pageable = PageRequest.of(page, total, Sort.by("publishTime").descending());
        return movieRepository.findByCategoriesLike(categories,pageable).getContent();
    }

    @Override
    public List<Movie> getAllByDescriptionLike(int page, int total, String description) {
        Pageable pageable = PageRequest.of(page, total, Sort.by("publishTime").descending());
        return movieRepository.findByDescriptionLike(description,pageable).getContent();
    }

    @Override
    public List<Movie> getAllByLocation(int page, int total, String location) {
        Pageable pageable = PageRequest.of(page, total, Sort.by("publishTime").descending());
        return movieRepository.findByLocation(location, pageable).getContent();
    }
}
