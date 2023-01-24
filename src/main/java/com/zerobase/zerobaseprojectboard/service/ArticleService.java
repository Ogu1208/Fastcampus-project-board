package com.zerobase.zerobaseprojectboard.service;

import com.zerobase.zerobaseprojectboard.domain.Article;
import com.zerobase.zerobaseprojectboard.domain.type.SearchType;
import com.zerobase.zerobaseprojectboard.dto.ArticleDto;
import com.zerobase.zerobaseprojectboard.dto.ArticleWithCommentsDto;
import com.zerobase.zerobaseprojectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE ->
                    articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT ->
                    articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID ->
                    articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME ->
                    articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG ->
                    articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        try {
        Article article = articleRepository.getReferenceById(dto.id());
        if (dto.title() != null) { article.setTitle(dto.title()); }
        if (dto.content() != null) { article.setContent(dto.content()); }
        article.setHashtag(dto.hashtag());
//        articleRepository.save(article);
        // save 따로 작성 x -> class level 트랜잭션에 의해서 메소드 단위로 트랜잭션이 묶여있음.
        // 따라서 트랜잭션이 끝날 때 영속성 컨택스트는 Article이 변한 것을 감지함
        // -> 쿼리를 날림 (update 쿼리가 실행됨)
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

}
