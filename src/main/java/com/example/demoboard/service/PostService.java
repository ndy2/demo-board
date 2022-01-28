package com.example.demoboard.service;

import com.example.demoboard.domain.*;
import com.example.demoboard.domain.dto.PostContentDto;
import com.example.demoboard.domain.dto.PostDisplayDto;
import com.example.demoboard.domain.dto.PostDto;
import com.example.demoboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long post(Post post) {
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


    public PostContentDto findContentDtoByIdFetchWriter(Long postId){
        PostContentDto postContentDto = postRepository.findByIdFetchWriterDto(postId);
        postContentDto.convertNewLine();
        return  postContentDto;
    }

}
