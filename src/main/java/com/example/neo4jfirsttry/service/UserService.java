package com.example.neo4jfirsttry.service;

import com.example.neo4jfirsttry.domain.User;
import com.example.neo4jfirsttry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getById(String userId) {
        return userRepository.findById(userId).get();
    }

    public List<User> getUserWhoHasCommentsAndPosts() {
        return userRepository.findUserWhoCommentsAndPosts();
    }

}
