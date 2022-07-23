package com.example.neo4jfirsttry.contoller;

import com.example.neo4jfirsttry.domain.Post;
import com.example.neo4jfirsttry.domain.User;
import com.example.neo4jfirsttry.facade.PostManager;
import com.example.neo4jfirsttry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;
    private PostManager postManager;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPostManager(PostManager postManager) {
        this.postManager = postManager;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/")
    public List<User> showAllUsers() {
        return userService.findAll();
    }

    @PostMapping(path = "/{userId}/posts")
    public Post createNewPost(@PathVariable("userId") String userId,
                              @RequestBody Post post) {
        return postManager.addUserNewPost(userId, post);
    }

    @GetMapping(path = "/{userId}/posts")
    public List<Post> getUserPosts(@PathVariable("userId") String userId){
        return postManager.getUserPosts(userId);
    }

    @GetMapping(path = "/veryActive")
    public List<User> getVeryActiveUsers() {
        return userService.getUserWhoHasCommentsAndPosts();
    }

}
