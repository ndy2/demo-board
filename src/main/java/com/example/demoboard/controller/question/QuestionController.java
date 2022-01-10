package com.example.demoboard.controller.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

    @GetMapping("qna/form")
    public String QuestionForm(){
        return "qna/form";
    }

    @GetMapping("qna/show")
    public String QuestionShow(){
        return "qna/show";
    }
}
