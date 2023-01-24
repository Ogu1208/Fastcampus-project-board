package com.zerobase.zerobaseprojectboard.controller;

import com.zerobase.zerobaseprojectboard.config.SecurityConfig;
import com.zerobase.zerobaseprojectboard.dto.ArticleWithCommentsDto;
import com.zerobase.zerobaseprojectboard.dto.UserAccountDto;
import com.zerobase.zerobaseprojectboard.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private final MockMvc mvc;

    @MockBean
    private ArticleService articleService;  // field 주입

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    } // 생성자 주입


    //    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 리스트 (게시판) - 정상 호출")
    @Test
    public void givenNothing_whenRequestArticlesView_thenReturnsArticlesView() throws Exception {
        // given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());

        //when & then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))  // view -> text_html
                .andExpect(view().name("articles/index"))  // view 파일 이름 검사
                .andExpect(model().attributeExists("articles"));  // view에 model_attirute로 articles를 내려준 것을 확인
        BDDMockito.then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));

    }

    //    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestArticleView_thenReturnsArticleView() throws Exception {
        // given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        //when & then
        mvc.perform(get("/articles/" + articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))  // view -> text_html
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))  // view에 model_attirute로 articles를 내려준 것을 확인
                .andExpect(model().attributeExists("articleComments"));
        BDDMockito.then(articleService).should().getArticle(articleId);

    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        // given

        //when & then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) // view -> text_html
                .andExpect(model().attributeExists("article/search"));


    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
        // given

        //when & then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))// view -> text_html
                .andExpect(model().attributeExists("article/search-hashtag"));


    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(1L,
                "ogu",
                "pw",
                "ogu@mail.com",
                "Ogu",
                "memo",
                LocalDateTime.now(),
                "ogu",
                LocalDateTime.now(),
                "ogu"
        );
    }
}