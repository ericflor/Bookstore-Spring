package com.example.SpringReactBookstore.repositories;

import com.example.SpringReactBookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookDAO extends JpaRepository<Book, UUID> {

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByTitleIgnoreCase(String title);

}
