package com.example.demoboard.controller;

import com.example.demoboard.domain.PostDisplayDto;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(@PageableDefault(sort = "id",direction = Sort.Direction.DESC,size = 5) Pageable pageable,
                       Model model){
        Page<PostDisplayDto> posts= postService.findPostDisplayDto(pageable.previousOrFirst());
        model.addAttribute("posts",posts);

        return "index";
    }
}
