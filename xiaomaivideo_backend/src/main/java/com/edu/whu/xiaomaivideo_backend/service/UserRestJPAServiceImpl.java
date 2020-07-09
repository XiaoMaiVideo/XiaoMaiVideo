package com.edu.whu.xiaomaivideo_backend.service;



import com.edu.whu.xiaomaivideo_backend.dao.UserRepository;
import com.edu.whu.xiaomaivideo_backend.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        return userRepository.getByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }
}
