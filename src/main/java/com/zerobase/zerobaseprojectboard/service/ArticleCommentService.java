package com.zerobase.zerobaseprojectboard.service;

import com.zerobase.zerobaseprojectboard.dto.ArticleCommentDto;
import com.zerobase.zerobaseprojectboard.repository.ArticleCommentRepository;
import com.zerobase.zerobaseprojectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto of) {
    }

    public void updateArticleComment(ArticleCommentDto of) {
    }

    public void deleteArticleComment(Long articleCommentId) {
    }
}
