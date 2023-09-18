package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity  //DB가 해당 객체를 인식 가능! => @Entity를 적용하면 그 대상 클래스명으로 테이블이 생성됨.
@AllArgsConstructor
@ToString
@NoArgsConstructor // 디폴트 생성자 추가 어노테이션. JPA에서 기본적으로 디폴트 생성자를 사용하게 되있음.
@Getter
public class Article {

    @Id // 대표값을 지정! like a 주민등록번호
    //@GeneratedValue // 1, 2, 3...  자동 생성 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동 생성하도록 변경
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


    public void patch(Article article) {
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }
}
