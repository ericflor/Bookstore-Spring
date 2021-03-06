package com.example.SpringReactBookstore.services;

import com.example.SpringReactBookstore.models.Book;
import com.example.SpringReactBookstore.models.BookDTO;
import com.example.SpringReactBookstore.repositories.BookDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.DirtiesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookDAO bookDAO;

    @Mock
    private ModelMapper mapper;

    @Test
    void shouldReturnListOfBookDTOWhenGetBooksIsCalled() {

        List<Book> books = new ArrayList<>();

        Book book = getBook();

        books.add(book);

        when(bookDAO.findAll()).thenReturn(books);

        BookDTO bookDTO = getBookDTO();

        when(mapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        List<BookDTO> bookDTOs = bookService.getBooks();

        assertThat(1).isEqualTo(bookDTOs.size());

        assertThat(bookDTOs.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "Harry Potter TEST")
                .hasFieldOrPropertyWithValue("description", "All your fantasies come true! TEST")
                .hasFieldOrPropertyWithValue("releaseYear", 2005);

    }
    @Test
    void shouldReturnBooksByTitle_IgnoreCase(){

        List<Book> books = new ArrayList<>();

        Book book = getBook();

        books.add(book);

        BookDTO bookDTO = getBookDTO();

        when(bookDAO.findBooksByTitleIgnoreCase(anyString())).thenReturn(books);

        when(mapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        List<BookDTO> bookDTOList = bookService.getBooksByTitle("HARRY POTTER TEST");

        assertThat(bookDTOList.size()).isEqualTo(1);
    }

    private Book getBook(){

        return Book.builder()
                .title("Harry Potter TEST")
                .description("All your fantasies come true! TEST")
                .id(UUID.randomUUID())
                .releaseYear(2005).build();
    }

    private BookDTO getBookDTO(){

        return BookDTO.builder()
                .title("Harry Potter TEST")
                .description("All your fantasies come true! TEST")
                .id(UUID.randomUUID())
                .releaseYear(2005).build();
    }
}