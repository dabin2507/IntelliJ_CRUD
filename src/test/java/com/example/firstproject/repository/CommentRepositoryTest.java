package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // JPA와 연동한 테스트!
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 수행. 레파지토리가 findByArticleId를 통해서 comments를 리턴할거다.
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글11");
            Comment a = new Comment(1L, article, "Park", "엘리멘탈");
            Comment b = new Comment(2L, article, "Lee", "무빙");
            Comment c = new Comment(3L, article, "Choi", "밀수");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!");
        }

        /* Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비. articleId가 1번
            Long articleId = 1L;

            // 실제 수행. 레파지토리가 findByArticleId를 통해서 comments를 리턴할거다.
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음!");
        }

        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {}

        /* Case 4: 9999번 게시글의 모든 댓글 조회 */
        {}

        /* Case 5: -1번 게시글의 모든 댓글 조회 */
        {}
    }

        @Test
        @DisplayName("특정 닉네임의 모든 댓글 조회")
        void findByNickname() {
            /* Case 1: "Park"의 모든 댓글 조회 */
            {
                // 입력 데이터를 준비
                String nickname = "Park";

                // 실제 수행. 레파지토리를 통해 닉네임을 가지고 댓글을 찾는다. 그래서 닉네임을 입력값으로 넣어줌.
                // 이게 반환한게 comments가 되고 이 comments의 타입은 Comment의 List이다.
                List<Comment> comments = commentRepository.findByNickname(nickname);

                // 예상하기
                Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글11"), nickname, "엘리멘탈");
                Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글22"), nickname, "초밥");
                Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글33"), nickname, "조깅");
                List<Comment> expected = Arrays.asList(a, b, c);

                // 검증
                assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
            }

            /* Case 2: "Lee"의 모든 댓글 조회 */
            {}

            /* Case 3: null의 모든 댓글 조회 */
            {}

            /* Case 4: ""의 모든 댓글 조회 */
            {}

            /* Case 5: "i"가 들어가있는 모든 댓글 조회 */
            {}

        }


}