package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j //로깅을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString()); -> 로깅으로 대체

        //1. dto를 Entity로 변환!
        Article article = form.toEntity(); //form을 가지고 toEntity()메서드 호출해서 Article이라는 타입의 Entity반환.
        log.info(article.toString());
        //System.out.println(article.toString());

        //2. Reposiroty에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){  //url 요청을 파라미터로 받아올 경우 @PathVariable 어노테이션 사용.
        log.info("id = " + id);

        // 1. id로 데이터를 가져옴. id값으로 찾았는데 해당 id가 없으면 null값을 넣어라.
        Article articleEntity = articleRepository.findById(id).orElse(null); //id값으로 데이터 조회

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 Article을 가져온다.
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 Article 묶음을 뷰로 전달한다. = 모델 사용
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지를 설정한다.
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){  //Long타입의 아이디를 가져오는데 어디서 가져오냐? url패스에서 가져온다.
        // 수정할 데이터 보여줘야함 -> 수정할 데이터 가져오기 -> 레파지토리를 통해 db에 있는 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 데이터 가져왔으니까 뷰페이지에서 사용할 수 있게 해줘야함 => 모델에 데이터를 등록해서.
        model.addAttribute("article", articleEntity);

        // 뷰페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")  //해당 메서드가 바껴진 데이터를 articleForm이라는 dto로 받아올 수 있었고 받아온 걸 엔터티로 변환.
    public String update(ArticleForm form){ // 폼 데이터를 dto로 받는다. id값이 추가 댔으니 dto에도 id값 추가해야함.
        log.info(form.toString()); // 데이터 받아온 것 확인

        // 1. dto를 엔터티로 변환한다
        Article articleEntity = form.toEntity(); // Article 타입의 articleEntity 라는 이름으로 받아온다.
        log.info(articleEntity.toString());

        // 2. 엔터티를 db로 저장한다
        // 2-1. db에서 기존 데이터를 가져온다
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터의 값을 갱신한다
        if(target != null){
            articleRepository.save(articleEntity); // 엔티티가 db로 갱신됨
        }

        // 3. 수정 결과 페이지로 리다이렉트한다
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")  // 매핑을 통해서 요청을 받아와야함.
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제요청이 들어왔습니다.");

        // 1. 삭제 대상을 가져온다. db에게 일을 하려면 jpa 리파지토리를 통해 대상이 있는지 찾아야함.
        Article target = articleRepository.findById(id).orElse(null);  //여기서 id는 url에서 가져올 수 있다. -> 파라미터로 추가
        log.info(target.toString());

        // 2. 대상을 삭제한다.
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        // 3. 결과 페이지로 리다이렉R트 한다.
        return "redirect:/articles";
    }



}
