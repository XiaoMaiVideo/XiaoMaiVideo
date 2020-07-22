/**
 * Author: 张俊杰
 * Create Time: 2020/7/8
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo_backend.dao;

import com.edu.whu.xiaomaivideo_backend.model.Movie;
import com.edu.whu.xiaomaivideo_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    User findDistinctByUsername(String username);

    Page<User> findByNicknameLike(String nickname, Pageable pageable);

}