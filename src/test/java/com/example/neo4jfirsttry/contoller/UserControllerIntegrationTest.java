package com.example.neo4jfirsttry.contoller;

import com.example.neo4jfirsttry.BaseIntegrationTest;
import com.example.neo4jfirsttry.domain.Comment;
import com.example.neo4jfirsttry.domain.Post;
import com.example.neo4jfirsttry.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserController userController;

    @Test
    public void createUser() {
        User neo = saveNewUser("Neo");

        List<User> users = userController.showAllUsers();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getLogin()).isEqualTo(neo.getLogin());
    }

    @Test
    public void getAllUsers() {
        User neo = saveNewUser("Neo");
        User trinity = saveNewUser("Trinity");
        User morpheus = saveNewUser("Morpheus");

        List<User> users = userController.showAllUsers();
        assertThat(users).hasSize(3);
        assertThat(users).containsOnly(neo, trinity, morpheus);
    }

    @Test
    public void createNewPostForUser() {
        User neo = saveNewUser("Neo");

        Post post = new Post();
        post.setTitle("Red or Blue");
        post.setContent("Which should I choose?");

        Post createdPost = userController.createNewPost(neo.getLogin(), post);

        assertThat(createdPost.getAuthor()).isEqualTo(neo);
        assertThat(createdPost.getComments()).isNullOrEmpty();
        assertThat(createdPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(createdPost.getContent()).isEqualTo(post.getContent());
        assertThat(createdPost.getId()).isNotNull();
    }

    @Test
    public void getUserPosts() {
        User neo = saveNewUser("Neo");
        Post post1 = new Post("Red or Blue", "Which should I choose?");
        Post post2 = new Post("Advice", "If I Were You, I Would Hope We Don't Meet Again.");
        userController.createNewPost(neo.getLogin(), post1);
        userController.createNewPost(neo.getLogin(), post2);

        User monk = saveNewUser("Monk");
        Post monkPost = new Post("Spoon", "Do not try and bend the spoon—that's impossible. Instead, only try to realize the truth.");
        userController.createNewPost(monk.getLogin(), monkPost);

        List<Post> neoPosts = userController.getUserPosts(neo.getLogin());
        assertThat(neoPosts).hasSize(2);
        assertThat(neoPosts).containsOnly(post1, post2);

        List<Post> monkPosts = userController.getUserPosts(monk.getLogin());
        assertThat(monkPosts).hasSize(1);
        assertThat(monkPosts).containsOnly(monkPost);
    }

    @Test
    public void getVeryActiveUsersReturnsNothingForNoPosts() {
        saveNewUser("Neo");

        assertThat(userController.getVeryActiveUsers()).isEmpty();
    }

    @Test
    public void getVeryActiveUsersReturnsNothingForPostsOnly() {
        User neo = saveNewUser("Neo");
        Post post = new Post("Red or Blue", "Which should I choose?");
        userController.createNewPost(neo.getLogin(), post);

        assertThat(userController.getVeryActiveUsers()).isEmpty();
    }

    @Test
    public void getVeryActiveUsersReturnsNothingForPostsOnlyAndCommentOnly() {
        User joker = saveNewUser("Joker");
        Comment comment = new Comment();
        comment.setAuthor(joker);
        comment.setContent("Obviously green!");

        User neo = saveNewUser("Neo");
        Post post = new Post("Red or Blue", "Which should I choose?");
        post.getComments().add(comment);
        userController.createNewPost(neo.getLogin(), post);

        assertThat(userController.getVeryActiveUsers()).isEmpty();
    }

    @Test
    public void getVeryActiveUsers() {
        User joker = saveNewUser("Joker");
        Post jokerPost = new Post("The question", "Why so serious?");
        userController.createNewPost(joker.getLogin(), jokerPost);

        Comment comment = new Comment();
        comment.setAuthor(joker);
        comment.setContent("Obviously green!");

        User neo = saveNewUser("Neo");
        Post post = new Post("Red or Blue", "Which should I choose?");
        post.getComments().add(comment);
        userController.createNewPost(neo.getLogin(), post);

        List<User> activeUsers = userController.getVeryActiveUsers();
        assertThat(activeUsers).hasSize(1);
        assertThat(activeUsers).containsOnly(joker);
    }

    private User saveNewUser(String login) {
        User neo = new User();
        neo.setLogin(login);

        return userController.createUser(neo);
    }

}