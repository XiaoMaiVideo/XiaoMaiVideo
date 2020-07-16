/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo_backend.service;

import com.edu.whu.xiaomaivideo_backend.dao.CommentRepository;
import com.edu.whu.xiaomaivideo_backend.model.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentRestJPAServiceImpl implements CommentRestService {
    @Resource
    private CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
