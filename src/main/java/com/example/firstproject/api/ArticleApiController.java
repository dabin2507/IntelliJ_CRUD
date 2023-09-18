package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController // RestAPI용 컨트롤러! 데이터(JSON)를 반환
public class ArticleApiController {

    @Autowired //DI, 생성 객체를 가져와 연결!
    private ArticleService articleService;

//    @Autowired // 스프링부트에서 땡겨와야함. DI.
//    private ArticleRepository articleRepository;
//
//    // GET
//    @GetMapping("/api/articles")
//    public List<Article> index(){
//        return articleRepository.findAll(); //모든 아티클 리턴. 레파지토리를 통해서.
//    }

    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index(); // 기존에는 레파지토리를 통해서 가져왔다면 이제 서비스를 통해 가져온다.
    }

//    @GetMapping("/api/articles/{id}")
//    public Article index(@PathVariable Long id){ // 단일 데이터라 List 없앰.
//        return articleRepository.findById(id).orElse(null);
//    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }
//
//    // POST
//    @PostMapping("/api/articles")
//    public Article create(@RequestBody ArticleForm dto){ // RestAPI에서 json으로 데이터를 던질때는 그냥 안받아짐.
//                                                         // @RequestBody 추가해야함. 리퀘스트바디에서 dto로 받아와라.
//        Article article = dto.toEntity(); // dto를 엔터티로 변환.
//        return articleRepository.save(article); // 클라이언트가 전송한 article을 저장.
//    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//    // PATCH
//    @PatchMapping("/api/articles/{id}")
//    // ResponseEntity에 Article 데이터가 담겨서 json으로 반환되게한다. -> Article을 ResponseEntity에 담아서 보내줘야됨. 상태코드를 실어 보낼 수 있음.
//    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
//
//        // 1. 수정용 엔티티 생성
//        Article article = dto.toEntity();  // article을 엔티티로 변경한다.
//        log.info("id: {}, article: {}", id, article.toString());
//
//        // 2. 대상 엔티티를 조회
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
//        if(target == null || id != article.getId()){
//            // 400번. 잘못된 요청 응답
//            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
//            // 400으로 보내도 되는데 HttpStatus.BAD_REQUEST 직관적으로 표현. 바디에는 아무것도 안실어서.
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 4. 업데이트 및 정상 응답(200)
//        // 타겟을 patch로 붙여준다. 우리가 받아온 article 엔티티로.
//        target.patch(article);
//        Article updated = articleRepository.save(target); // 타겟. 기존에 있던 거에 새롭게 붙여진 것을 저장한다.
//        // ResponseEntity에 담아서 보내준다. 바디에 update를 실어서.
//        return ResponseEntity.status(HttpStatus.OK).body(updated); //
//    }


     //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){ // 컨트롤러는 무엇을 받아오고
        Article updated = articleService.update(id, dto); // 요리는 서비스에 맡김. 서비스 클래스에 서비스 메서드 구현해야함.
        return (updated != null) ?  // 무엇을 반환하는지만 알면 됨
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
    }

//
//    // DELETE
//    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Article> delete(@PathVariable Long id){
//
//        // 대상 찾기
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 잘못된 요청 처리
//        if(target == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 대상 삭제
//        articleRepository.delete(target);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//

     // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    // 트랜잭션 -> 실패 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) { // 여러개의 아티클 데이터 받아와야함
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
