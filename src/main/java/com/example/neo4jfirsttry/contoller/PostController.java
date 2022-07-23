package com.example.neo4jfirsttry.contoller;

import com.example.neo4jfirsttry.domain.Comment;
import com.example.neo4jfirsttry.domain.Post;
import com.example.neo4jfirsttry.facade.PostManager;
import com.example.neo4jfirsttry.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private PostService postService;
    private PostManager postManager;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setPostManager(PostManager postManager) {
        this.postManager = postManager;
    }

    @GetMapping("/")
    public List<Post> getAllPosts() {
        return postService.getAll();
    }

    @GetMapping(path = "{postId}")
    public Post getPostById(@PathVariable("postId") Long id) {
        return postService.getById(id);
    }

    @PostMapping
    public Post addNewPost(@RequestBody Post post) {
        return postService.save(post);
    }

    @DeleteMapping(path = "{postId}")
    public void deletePostById(@PathVariable("postId") Long id) {
        postService.deleteById(id);
    }

    @PutMapping(path = "{postId}")
    public Post changePost(@PathVariable("postId") Long id,
                           @RequestParam(name = "title") String title) throws Exception {
        return postService.updateTitle(id, title);
    }

    @PostMapping(path = "/{postId}/comments")
    public Post addComment(@PathVariable("postId") Long postId,
                              @RequestParam(name = "userId") String userId,
                              @RequestBody Comment comment) {
        return postManager.addCommentFromUser(postId, userId, comment);
    }

}
