/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */

package com.edu.whu.xiaomaivideo_backend.service;

import com.edu.whu.xiaomaivideo_backend.dao.CommitRepository;
import com.edu.whu.xiaomaivideo_backend.model.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommitRestJPAServiceImpl implements CommitRestService {
    @Resource
    private CommitRepository commitRepository;

    @Override
    public Comment saveCommit(Comment comment) {
        return commitRepository.save(comment);
    }
}
