package com.example.demoboard.service;

import com.example.demoboard.domain.*;
import com.example.demoboard.repository.AccountRepository;
import com.example.demoboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static com.example.demoboard.domain.Post.createPost;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long post(Long writerId, PostUploadDto postUploadDto) {
        Post post = createPost(accountRepository.findById(writerId).get(), postUploadDto.getTitle(), postUploadDto.getContents());
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
