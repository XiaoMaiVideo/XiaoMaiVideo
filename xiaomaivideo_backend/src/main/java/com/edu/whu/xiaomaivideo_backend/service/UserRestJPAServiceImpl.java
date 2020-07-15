/**
 * Author: 张俊杰
 * Create Time: 2020/7/8
 * Update Time: 2020/7/15
 */


package com.edu.whu.xiaomaivideo_backend.service;



import com.edu.whu.xiaomaivideo_backend.dao.UserRepository;
import com.edu.whu.xiaomaivideo_backend.model.Movie;
import com.edu.whu.xiaomaivideo_backend.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class UserRestJPAServiceImpl implements UserRestService {
    @Resource
    private UserRepository userRepository;


    @Override
    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findDistinctByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
