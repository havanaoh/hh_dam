package com.hh.dam.repository;

import com.hh.dam.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    // Board의 boardId를 기준으로 게시물 조회
    List<Post> findByBoard_BoardId(int boardId);

    // 특정 작성자의 게시물 조회
    List<Post> findByMemberId(int memberId);

    // Board의 groupId(카테고리분류)를 기준으로 게시물 조회
    List<Post> findByBoard_GroupId(int groupId);

    // 특정 추천 수 기준으로 인기 게시물 조회 (추천 수 내림차순 정렬)
    // 추천 수가 10 이상인 인기 게시물 조회 (조회 수 내림차순 정렬)
    @Query("SELECT p FROM Post p WHERE p.plikes >= 10 ORDER BY p.views DESC")
    List<Post> findPopularPosts();

    // 특정 게시물 삭제
    int deleteByPostId(int postId);
}
