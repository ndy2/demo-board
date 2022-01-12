package com.example.demoboard.controller;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.PostDisplayDto;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/denied")
    public String accessDenied(@RequestParam(required = false) String exception,
                               Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account)authentication.getPrincipal();

        model.addAttribute("username", account.getUsername());
        model.addAttribute("exception",exception);

        return "user/login/denied";
    }
}
