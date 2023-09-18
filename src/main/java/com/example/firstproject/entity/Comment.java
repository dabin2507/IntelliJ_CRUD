package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성. DB가 자동으로 하나씩 증가할 수 있게함.
    private Long id;

    @ManyToOne // 해당 댓글 여러개의 Comment가 하나의 Article에 연관된다.
    @JoinColumn(name = "article_id") // 테이블에서 연결된 대상의 정보를 가져야하는데 대성 정보의 컬럼을 article_id라고 하겠다.
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body; // 댓글 내용

}
