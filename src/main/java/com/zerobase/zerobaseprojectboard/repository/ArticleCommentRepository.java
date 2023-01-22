package com.zerobase.zerobaseprojectboard.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.zerobase.zerobaseprojectboard.domain.ArticleComment;
import com.zerobase.zerobaseprojectboard.domain.QArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource  // Spring Data REST
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment>
{
    List<ArticleComment> findByArticle_Id(Long articleId);  // JPA 문법(_)
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        bindings.excludeUnlistedProperties(true); // 선택적 필터 (기본적으로는 모든 필드에 대해 열려있음 - default:false)
        bindings.including( root.content, root.createdAt, root.createdBy);// like '${value}'  (쿼리 생성문이 다름)
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);  // like '%${value}%' 대소문자 구분 x
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
