package com.example.SpringReactBookstore.services;

import com.example.SpringReactBookstore.models.Book;
import com.example.SpringReactBookstore.models.BookDTO;
import com.example.SpringReactBookstore.repositories.BookDAO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
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

}
