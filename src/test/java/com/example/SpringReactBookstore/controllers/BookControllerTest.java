package com.example.SpringReactBookstore.controllers;

import com.example.SpringReactBookstore.models.BookDTO;
import com.example.SpringReactBookstore.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Test
    void shouldReturnBookDTOListWhenGetBooksIsCalled(){

        List<BookDTO> bookDTOs = new ArrayList<>();

        bookDTOs.add(getBookDTO());

        when(bookService.getBooks()).thenReturn(bookDTOs);

        ResponseEntity<List<BookDTO>> allBooks = bookController.getAllBooks();

        assertThat(allBooks).isNotNull();

        assertThat(Objects.requireNonNull(allBooks.getBody()).size()).isEqualTo(1);
    }

    private BookDTO getBookDTO(){

        return BookDTO.builder()
                .title("Harry Potter TEST")
                .description("All your fantasies come true! TEST")
                .id(UUID.randomUUID())
                .releaseYear(2005).build();
    }

}