package com.zerobase.zerobaseprojectboard.service;

import com.zerobase.zerobaseprojectboard.domain.Article;
import com.zerobase.zerobaseprojectboard.domain.ArticleComment;
import com.zerobase.zerobaseprojectboard.domain.UserAccount;
import com.zerobase.zerobaseprojectboard.dto.ArticleCommentDto;
import com.zerobase.zerobaseprojectboard.dto.UserAccountDto;
import com.zerobase.zerobaseprojectboard.repository.ArticleCommentRepository;
import com.zerobase.zerobaseprojectboard.repository.ArticleRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;

    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;


    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));

        // when
        List<ArticleCommentDto> actual = sut.searchArticleComments(articleId);

        // then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSaveArticleComment() {
        // given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // when
        sut.saveArticleComment(dto);

        // then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안 한다.")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsWarningAndDoesNothing() {
        // given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);

        // when
        sut.saveArticleComment(dto);

        // then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
        // given
        String oldContent = "content";
        String updatedContent = "수정댓글";
        ArticleComment articleComment = createArticleComment(oldContent);
        ArticleCommentDto dto = createArticleCommentDto(updatedContent);
        given(articleCommentRepository.getReferenceById(dto.id())).willReturn(articleComment);

        // when
        sut.updateArticleComment(dto);

        // then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무 것도 안한다.")
    @Test
    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing() {
        // given
        ArticleCommentDto dto = createArticleCommentDto("수정 댓글");
        given(articleCommentRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // when
        sut.updateArticleComment(dto);

        // then
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentRepository).deleteById(articleCommentId);
        // when
        sut.deleteArticleComment(articleCommentId);

        // then
        then(articleCommentRepository).should().deleteById(articleCommentId);
    }

    private ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L,
                1L,
                createUserAccountDto(),
                content,
                LocalDateTime.now(),
                "ogu",
                LocalDateTime.now(),
                "ogu"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "ogu",
                "password",
                "ogu@mail.com",
                "Ogu",
                "This is memo",
                LocalDateTime.now(),
                "ogu",
                LocalDateTime.now(),
                "ogu"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.of(
                Article.of(createUserAccount(), "title", "content", "hashtag"),
                createUserAccount(),
                content
        );
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "Ogu",
                "password",
                "Ogu@email.com",
                "Ogu",
                null
        );
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

}