package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// <>안에 앞에는 관리할대상, 대상의 fk의 타입 Long
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회
    @Query(value =
            "SELECT * " +
                    "FROM comment " +
                    "WHERE article_id = :articleId",
            nativeQuery = true) // native트루로 해줘야 해당 sql문 동작함.
    List<Comment> findByArticleId(@Param("articleId") Long articleId); // articleId를 입력했을 때 Comment의 묶음을 반환

    // 특정 닉네임의 모든 댓글 조회. xml로 작성.
    @Query(name = "Comment.findByNickname")
    List<Comment> findByNickname(@Param("nickname")String nickname); // 입력값으로 nicname을 넘겨주면 코멘트의 묶음을 반환해라.

}
