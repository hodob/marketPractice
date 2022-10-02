package com.hodob.marketPractice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hodob.marketPractice.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.author.id = ?#{principal.account.id}")
    List<Book> findCurrentUserBooks();
}
