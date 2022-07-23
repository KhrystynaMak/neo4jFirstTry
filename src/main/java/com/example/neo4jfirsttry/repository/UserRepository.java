package com.example.neo4jfirsttry.repository;

import com.example.neo4jfirsttry.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, String> {

    @Query("MATCH (u:User)-[:AUTHOR]->(c:Comment), (u)-[:AUTHOR]->(p:Post) RETURN DISTINCT u")
    List<User> findUserWhoCommentsAndPosts();
}
