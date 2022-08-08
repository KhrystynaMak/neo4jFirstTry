package com.example.neo4jfirsttry.repository;

import com.example.neo4jfirsttry.domain.Comment;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CommentRepository extends Neo4jRepository<Comment, Long> {
}
