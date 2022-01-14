package com.example.demoboard.repository;

import com.example.demoboard.domain.Comment;
import com.example.demoboard.domain.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select c" +
            " from Comment c join fetch c.writer" +
            " where c.post.id =:postId")
    List<Comment> findAllByPostIdFetchWriter(@Param("postId") Long postId);

    @Query("select new com.example.demoboard.domain.CommentDto(w.id, w.name,c.contents,c.createDate,c.id)" +
            " from Comment c join c.writer w" +
            " where c.post.id =:postId")
    List<CommentDto> findAllByPostIdFetchWriterDto(@Param("postId") Long postId);
}
