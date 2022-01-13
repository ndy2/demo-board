package com.example.demoboard.service;

import com.example.demoboard.domain.*;
import com.example.demoboard.repository.AccountRepository;
import com.example.demoboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.demoboard.domain.Post.createPost;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long post(Long writerId, PostDto postDto) {
        Post post = createPost(accountRepository.findById(writerId).get(), postDto.getTitle(), postDto.getContents());
        postRepository.save(post);
        return post.getId();
    }

    public Page<PostDisplayDto> findPostDisplayDto(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostDisplayDto::new);
    }

    public Post findById(Long id){
        return postRepository.findById(id).get();
    }

    public boolean isWrittenBy(Long postId, Long writerId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.isPresent()
                && postOptional.get().isWrittenBy(writerId);
    }

    @Transactional
    public void edit(Long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).get();
        post.edit(postDto.getTitle(),postDto.getContents());
    }

    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
