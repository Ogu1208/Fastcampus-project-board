package com.zerobase.zerobaseprojectboard.controller;

import com.zerobase.zerobaseprojectboard.config.SecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }


//    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 리스트 (게시판) - 정상 호출")
    @Test
    public void givenNothing_whenRequestArticlesView_thenReturnsArticlesView() throws Exception {
        // given

        //when & then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))  // view -> text_html
                .andExpect(view().name("articles/index"))  // view 파일 이름 검사
                .andExpect(model().attributeExists("articles"));  // view에 model_attirute로 articles를 내려준 것을 확인

    }

//    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestArticleView_thenReturnsArticleView() throws Exception {
        // given

        //when & then
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))  // view -> text_html
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))  // view에 model_attirute로 articles를 내려준 것을 확인
                .andExpect(model().attributeExists("articleComments"));

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
}