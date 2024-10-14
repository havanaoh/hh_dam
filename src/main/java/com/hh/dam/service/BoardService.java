package com.hh.dam.service;

import com.hh.dam.entity.Post;
import com.hh.dam.repository.BoardRepository;
import com.hh.dam.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final PostRepository postRepository;

    public BoardService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts(int boardId){
        return postRepository.findByBoardBoardId(boardId);
    }

}
