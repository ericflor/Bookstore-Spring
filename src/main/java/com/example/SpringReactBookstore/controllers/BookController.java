package com.example.SpringReactBookstore.controllers;

import com.example.SpringReactBookstore.models.BookDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(){

        BookDTO book = BookDTO.builder().title("Lord of the Rings").build();

        List<BookDTO> books = new ArrayList<>();

        books.add(book);

        return ResponseEntity.status(HttpStatus.OK).body(books);

    }
}
