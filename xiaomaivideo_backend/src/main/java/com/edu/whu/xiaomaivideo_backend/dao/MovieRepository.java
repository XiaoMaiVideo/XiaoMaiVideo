/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/22
 */


package com.edu.whu.xiaomaivideo_backend.dao;

import com.edu.whu.xiaomaivideo_backend.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie,Long> {
    Page<Movie> findAll(Pageable pageable);

    //根据categories字段查询表数据，传入Pageable分页参数
    Page<Movie> findByCategoriesLike(String categories, Pageable pageable);

    Page<Movie> findByLocation(String location, Pageable pageable);

    Page<Movie> findByDescriptionLike(String description, Pageable pageable);
}
