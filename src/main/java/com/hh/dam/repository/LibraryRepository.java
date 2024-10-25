package com.hh.dam.repository;

import com.hh.dam.entity.Book;
import com.hh.dam.entity.BookStatus;
import com.hh.dam.entity.Library;
import com.hh.dam.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {

    List<Library> findByMemberAndStatus(Member member, BookStatus status);

    Optional<Library> findByBookAndMember(Book book, Member member);
}
