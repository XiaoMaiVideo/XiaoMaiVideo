/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo_backend.service;

import com.edu.whu.xiaomaivideo_backend.model.Comment;

public interface CommentRestService {
    Comment saveComment(Comment comment);
}
