package com.hh.dam.repository;

import com.hh.dam.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 특정 그룹 ID에 해당하는 게시판 조회
    List<Board> findByGroupId(int groupId);

}
