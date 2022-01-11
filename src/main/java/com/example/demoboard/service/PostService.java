package com.example.demoboard.service;

import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.PostContentDto;
import com.example.demoboard.domain.PostDisplayDto;
import com.example.demoboard.domain.PostUploadDto;
import com.example.demoboard.repository.AccountRepository;
import com.example.demoboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long post(Long writerId, PostUploadDto postUploadDto) {
        Post post = new Post();
        post.setWriter(accountRepository.findById(writerId).get());
        post.setTitle(postUploadDto.getTitle());
        post.setContents(postUploadDto.getContents());
        post.setDateTime(LocalDateTime.now());

        postRepository.save(post);
        return post.getId();
    }

    public Page<PostDisplayDto> findPostDisplayDto(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostDisplayDto::new);
    }

    public PostContentDto findPostContentDtoById(Long postId) {
        return postRepository.findById(postId)
                .map(PostContentDto::new).get();
    }
}
