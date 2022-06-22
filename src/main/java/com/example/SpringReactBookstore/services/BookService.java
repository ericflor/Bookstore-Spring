package com.example.SpringReactBookstore.services;

import com.example.SpringReactBookstore.models.Book;
import com.example.SpringReactBookstore.models.BookDTO;
import com.example.SpringReactBookstore.repositories.BookDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private final BookDAO bookDAO;
    private final ModelMapper modelMapper;

    public BookService(BookDAO bookDAO, ModelMapper modelMapper) {
        this.bookDAO = bookDAO;
        this.modelMapper = modelMapper;
    }

    public List<BookDTO> getBooks(){

        List<Book> allBooks = bookDAO.findAll();

        return allBooks.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

    }

    public List<BookDTO> getBooksByTitle(String bookTitle) {

        List<Book> booksByTitle = bookDAO.findBooksByTitleIgnoreCase(bookTitle);

        return booksByTitle.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }
}
