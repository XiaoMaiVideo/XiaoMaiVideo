/**
 * Author: 张俊杰
 * Create Time: 2020/7/8
 * Update Time: 2020/7/22
 */


package com.edu.whu.xiaomaivideo_backend.service;
import com.edu.whu.xiaomaivideo_backend.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserRestService {
    User saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User getUser(String username);

    User getUserById(Long id);

    List<User> getAllUser();

    Page<User> getAllByNicknameLike(int page, int total, String nickname);
}
