package com.example.neo4jfirsttry.contoller;

import com.example.neo4jfirsttry.BaseIntegrationTest;
import com.example.neo4jfirsttry.domain.Comment;
import com.example.neo4jfirsttry.domain.Post;
import com.example.neo4jfirsttry.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostControllerIntegrationTest  extends BaseIntegrationTest {

    @Autowired
    private PostController postController;
    @Autowired
    private UserController userController;

    @Test
    public void listIsEmptyForNoPosts() {
        assertThat(postController.getAllPosts()).isEmpty();
    }

    @Test
    public void getAllPosts() {
        User joker = saveNewUser("Joker");
        Post jokerPost = new Post("The question", "Why so serious?");
        userController.createNewPost(joker.getLogin(), jokerPost);

        List<Post> posts = postController.getAllPosts();

        assertThat(posts).hasSize(1);
        assertThat(posts).containsOnly(jokerPost);

        User neo = saveNewUser("Neo");
        Post post = new Post("Red or Blue", "Which should I choose?");
        userController.createNewPost(neo.getLogin(), post);

        posts = postController.getAllPosts();

        assertThat(posts).hasSize(2);
        assertThat(posts).containsOnly(jokerPost, post);
    }

    @Test
    public void nullForNotExistedId() {
        assertThat(postController.getPostById(123L)).isNull();
    }

    @Test
    public void getPostById() {
        User joker = saveNewUser("Joker");
        Post jokerPost = new Post("The question", "Why so serious?");
        jokerPost.setAuthor(joker);

        postController.addNewPost(jokerPost);

        assertThat(postController.getPostById(jokerPost.getId())).isEqualTo(jokerPost);
    }

    @Test
    public void addNewPost() {
        User joker = saveNewUser("Joker");
        Post jokerPost = new Post("The question", "Why so serious?");
        jokerPost.setAuthor(joker);
        postController.addNewPost(jokerPost);

        Post savedPost = postController.getPostById(jokerPost.getId());

        assertThat(savedPost).isEqualTo(jokerPost);
        assertThat(savedPost.getCreated()).isNotNull();
    }

    @Test
    public void deletePostById() {
        User joker = saveNewUser("Joker");
        Post jokerPost1 = new Post("The question", "Why so serious?");
        jokerPost1.setAuthor(joker);
        postController.addNewPost(jokerPost1);

        Post jokerPost2 = new Post("Gravity", "As you know, madness is like gravity: All it takes is a little push.");
        jokerPost2.setAuthor(joker);
        postController.addNewPost(jokerPost2);

        assertThat(postController.getAllPosts()).hasSize(2);

        postController.deletePostById(jokerPost2.getId());

        assertThat(postController.getPostById(jokerPost2.getId())).isNull();
        assertThat(postController.getAllPosts()).hasSize(1);
    }

    @Test
    public void changePost() throws Exception {
        User joker = saveNewUser("Joker");
        Post jokerPost = new Post("The question", "Why so serious?");
        jokerPost.setAuthor(joker);
        postController.addNewPost(jokerPost);

        Post savedPost = postController.getPostById(jokerPost.getId());

        assertThat(savedPost).isEqualTo(jokerPost);
        assertThat(savedPost.getCreated()).isNotNull();

        String newTitle = "Smile";
        postController.changePost(jokerPost.getId(), newTitle);

        assertThat(postController.getPostById(jokerPost.getId()).getTitle()).isEqualTo(newTitle);
    }

    @Test
    public void noPostWithSuchId() {
        assertThrows(Exception.class, () -> postController.changePost(123L, "Fun"));
    }

    @Test
    public void addComment() {
        User joker = saveNewUser("Joker");
        Comment comment = new Comment();
        comment.setContent("Obviously green!");

        User neo = saveNewUser("Neo");
        Post post = new Post("Red or Blue", "Which should I choose?");
        userController.createNewPost(neo.getLogin(), post);

        postController.addComment(post.getId(), joker.getLogin(), comment);

        Post updatedPost = postController.getPostById(post.getId());
        assertThat(updatedPost.getComments()).hasSize(1);
        assertThat(updatedPost.getComments().get(0).getContent()).isEqualTo(comment.getContent());
        assertThat(updatedPost.getComments().get(0).getAuthor()).isEqualTo(joker);
    }

    private User saveNewUser(String login) {
        User neo = new User();
        neo.setLogin(login);

        return userController.createUser(neo);
    }

}