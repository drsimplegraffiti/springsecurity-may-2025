package com.drsimple.jwtsecurity.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Procedure(procedureName = "findBlogsByAuthor")
    List<Blog> findBlogsByAuthor(@Param("authorName") String authorName);
}