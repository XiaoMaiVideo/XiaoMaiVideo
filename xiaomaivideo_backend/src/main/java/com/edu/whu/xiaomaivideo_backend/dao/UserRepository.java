/**
 * Author: 张俊杰
 * Create Time: 2020/7/8
 * Update Time: 2020/7/8
 */

package com.edu.whu.xiaomaivideo_backend.dao;

import com.edu.whu.xiaomaivideo_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {

    User getByUsername(String username);
}