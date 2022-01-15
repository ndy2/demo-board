package com.example.demoboard.repository;

import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.dto.PostContentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAll(Pageable pageable);

    @Query("select p from Post p join fetch p.writer w where p.id=:postId")
    Post findByIdFetchWriter(@Param("postId") Long postId);

    @Query("select new com.example.demoboard.domain.dto.PostContentDto(p.id,w.name,w.id,p.createdDate,p.title,p.contents)" +
            " from Post p join p.writer w where p.id=:postId")
    PostContentDto findByIdFetchWriterDto(@Param("postId") Long postId);
}
