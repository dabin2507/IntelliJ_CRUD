package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service  // 서비스 선언(서비스 객체를 스프링부트에 생성)
public class ArticleService { // 해당 ArticleService가 Repository와 협업할 수 잇게 필드 추가

    @Autowired  // DI
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity(); // dto를 받았으면 이걸 엔티티로 바꾼다. 바꾼걸 article로 받아옴
        if (article.getId() != null)  // 아이디값이 있는 것을 post요청 했을 때 안되게 만드는 코드.
            return null;
        return articleRepository.save(article);//article을 db에 저장한다.
    }

    public Article update(Long id, ArticleForm dto) {
         //1. 수정용 엔티티 생성
        Article article = dto.toEntity();  // article을 엔티티로 변경한다.
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()){
            // 400번. 잘못된 요청 응답
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            // 400으로 보내도 되는데 HttpStatus.BAD_REQUEST 직관적으로 표현. 바디에는 아무것도 안실어서.
            return null;
        }

        // 4. 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;

    }

    public Article delete(Long id) {

        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if(target == null){
            return null;
        }

        // 대상 삭제 후 응답
        articleRepository.delete(target);
        return target;
    }

    @Transactional // 해당 메소드를 트랜잭션으로 묶는다! => 해당 메소드를 실행하다 실패하면 이전 상태로 롤백한다.
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // 위 문법을 for문으로 하면
//        List<Article> articleList = new ArrayList<>();
//        for(int i = 0; i < dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

        // entity 묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        // 위 문법을 for문으로 하면
//        for(int i = 0; i < articleList.size(); i++){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결재 실패!")
        );

        // 결과값 반환
        return articleList;

    }
}
