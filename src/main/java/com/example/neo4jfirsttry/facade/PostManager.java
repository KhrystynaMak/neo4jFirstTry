package com.example.neo4jfirsttry.facade;

import com.example.neo4jfirsttry.domain.Comment;
import com.example.neo4jfirsttry.domain.Post;
import com.example.neo4jfirsttry.domain.User;
import com.example.neo4jfirsttry.service.CommentService;
import com.example.neo4jfirsttry.service.PostService;
import com.example.neo4jfirsttry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostManager {
    private UserService userService;
    private CommentService commentService;
    private PostService postService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public Post addUserNewPost(String userId, Post post) {
        User user = userService.getById(userId);

        post.setAuthor(user);
        return postService.save(post);
    }

    public Post addCommentFromUser(Long postId, String userId, Comment comment) {
        comment.setAuthor(userService.getById(userId));
        Post post = postService.getById(postId);
        post.getComments().add(commentService.save(comment));
        return postService.save(post);
    }

    public List<Post> getUserPosts(String userId) {
        return postService.getPostsByUserId(userId);
    }
}
