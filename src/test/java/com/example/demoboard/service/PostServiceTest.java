package com.example.demoboard.service;

import com.example.demoboard.domain.AccountDto;
import com.example.demoboard.domain.PostDisplayDto;
import com.example.demoboard.domain.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired AccountService accountService;
    @Autowired PostService postService;

    @Test
    void pagingTest(){
        //given
        AccountDto accountDto = createAccountDto("득윤", "ndy2", "{noop}1234", "emrdbs12@gmail.com");
        Long id1 = accountService.register(accountDto);

        AccountDto accountDto2 = createAccountDto("하영", "bhy2", "{noop}1234", "bhy@gmail.com");
        Long id2 = accountService.register(accountDto2);

        for (int i = 0; i < 13; i++) {
            PostDto postDto = createPostUploadDto("나는 득윤 "+i);
            postService.post(id1, postDto);
        }

        for (int i = 0; i < 15; i++) {
            PostDto postDto = createPostUploadDto("나는 하영 "+i);
            postService.post(id2, postDto);
        }

        //when
        PageRequest pageRequest = PageRequest.of(1,10);
        Page<PostDisplayDto> result = postService.findPostDisplayDto(pageRequest);

        List<PostDisplayDto> postDisplayDtos = result.getContent();
        for (PostDisplayDto dto : postDisplayDtos) {
            System.out.println("dto = " + dto);
        }

        long count = result.getTotalElements();
        int numPages = result.getTotalPages();
        System.out.println("count = " + count + " numPages = " + numPages);

    }

    private PostDto createPostUploadDto(String title) {
        PostDto postDto1 = new PostDto();
        postDto1.setTitle(title);
        postDto1.setContents("나는 득윤 나는 득윤 나는 득윤 나는 득윤 나는 득윤 나는 득윤 나는 득윤");
        return postDto1;
    }

    private AccountDto createAccountDto(String name, String username, String password, String email) {
        AccountDto accountDto = new AccountDto();
        accountDto.setName(name);
        accountDto.setUsername(username);
        accountDto.setPassword(password);
        accountDto.setEmail(email);
        return accountDto;
    }
}