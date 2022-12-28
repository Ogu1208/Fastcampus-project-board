package com.zerobase.zerobaseprojectboard.repository;

import com.zerobase.zerobaseprojectboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
