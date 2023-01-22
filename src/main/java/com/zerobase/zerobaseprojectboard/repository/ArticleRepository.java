package com.zerobase.zerobaseprojectboard.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.zerobase.zerobaseprojectboard.domain.Article;
import com.zerobase.zerobaseprojectboard.domain.QArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource  // Spring Data REST
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,  // Article Field의 모든 필드에 대한 기본 검색 기능 추가
        QuerydslBinderCustomizer<QArticle>
{
    Page<Article> findByTitle(String title, Pageable pageable);
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // 선택적 필터 (기본적으로는 모든 필드에 대해 열려있음 - default:false)
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);// like '${value}'  (쿼리 생성문이 다름)
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);  // like '%${value}%' 대소문자 구분 x
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);  // like '%${value}%' 대소문자 구분 x
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
