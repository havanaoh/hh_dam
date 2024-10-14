package com.hh.dam.repository;

import com.hh.dam.entity.Board;
import com.hh.dam.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
