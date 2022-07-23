package com.example.neo4jfirsttry.repository;

import com.example.neo4jfirsttry.domain.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PostRepository extends Neo4jRepository<Post, Long> {

    List<Post> findByAuthor_Login(String login);


}
