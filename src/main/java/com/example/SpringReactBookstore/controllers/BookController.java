package com.example.SpringReactBookstore.controllers;

import com.example.SpringReactBookstore.models.BookDTO;
import com.example.SpringReactBookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(){

        List<BookDTO> books = bookService.getBooks();

        return ResponseEntity.status(HttpStatus.OK).body(books);

    }

    @GetMapping("/{title}")
    public ResponseEntity<List<BookDTO>> getBookByTitle(@PathVariable("title") String title) {

        List<BookDTO> booksByTitle = bookService.getBooksByTitle(title);

        return ResponseEntity.status(HttpStatus.OK).body(booksByTitle);
    }
}
