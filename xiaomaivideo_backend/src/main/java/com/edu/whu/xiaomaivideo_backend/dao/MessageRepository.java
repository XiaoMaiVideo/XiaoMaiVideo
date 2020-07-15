/**
 * Author: 张俊杰
 * Create Time: 2020/7/15
 * Update Time: 2020/7/15
 */

package com.edu.whu.xiaomaivideo_backend.dao;

import com.edu.whu.xiaomaivideo_backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
