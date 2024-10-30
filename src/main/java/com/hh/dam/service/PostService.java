package com.hh.dam.service;

import com.hh.dam.entity.Post;
import com.hh.dam.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 특정 추천 수 이상의 게시물 조회
    public List<Post> getPopularPosts() {
        return postRepository.findPopularPosts();
    }

    // 특정 게시판 ID의 게시물 조회
    public List<Post> getPostsByBoardId(int boardId) {
        return postRepository.findByBoard_BoardId(boardId);
    }

    // 특정 작성자의 게시물 조회
    public List<Post> getPostsByMemberId(int memberId) {
        return postRepository.findByMemberId(memberId);
    }

    // 특정 groupId에 해당하는 게시물 조회
    public List<Post> getPostsByGroupId(int groupId) {
        return postRepository.findByBoard_GroupId(groupId);
    }
}
