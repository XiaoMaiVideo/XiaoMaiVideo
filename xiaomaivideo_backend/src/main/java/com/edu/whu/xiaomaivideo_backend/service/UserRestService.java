package com.edu.whu.xiaomaivideo_backend.service;


import com.edu.whu.xiaomaivideo_backend.model.User;

public interface UserRestService {
    User saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User getUser(String username);

    User getUserById(Long id);
}
