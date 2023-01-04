package com.zerobase.zerobaseprojectboard.repository;

import com.zerobase.zerobaseprojectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource  // Spring Data REST
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
