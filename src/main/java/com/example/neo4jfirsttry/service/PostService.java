package com.example.neo4jfirsttry.service;

import com.example.neo4jfirsttry.domain.Comment;
import com.example.neo4jfirsttry.domain.Post;
import com.example.neo4jfirsttry.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;
    private CommentService commentService;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post getById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public Post save(Post post) {
        post.setCreated(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updateTitle(Long id, String title) throws Exception {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new Exception("There is no post with such id");
        }
        post.get().setTitle(title);
        return postRepository.save(post.get());
    }

    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByAuthor_Login(userId);
    }
}

