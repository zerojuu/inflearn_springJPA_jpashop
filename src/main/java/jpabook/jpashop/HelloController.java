package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")    //hello라는 url를 호출하면 이 controller를 호출
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!"); //model에 값 담기
        return "hello"; //화면이름.. templates 하위에 있는 hello.html..thymeleaf이기에 가능
    }
}
