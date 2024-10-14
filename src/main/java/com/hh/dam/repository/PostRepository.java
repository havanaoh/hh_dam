package com.hh.dam.repository;


import com.hh.dam.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByBoardBoardId(int boardId);

    int deleteByPostId(int postId);


}
