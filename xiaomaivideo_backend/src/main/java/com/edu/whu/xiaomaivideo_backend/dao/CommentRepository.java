/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo_backend.dao;

import com.edu.whu.xiaomaivideo_backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
