package com.zerobase.zerobaseprojectboard.repository;

import com.zerobase.zerobaseprojectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}