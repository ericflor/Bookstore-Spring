package com.example.SpringReactBookstore.controllers;

import com.example.SpringReactBookstore.SpringReactBookstoreApplication;
import com.example.SpringReactBookstore.config.JwtUtil;
import com.example.SpringReactBookstore.models.BookDTO;
import com.example.SpringReactBookstore.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = SpringReactBookstoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    void setUpHeader(){

        String token = jwtUtil.generateToken(new User("TheBob123@gmail.com", "1pass3word3", new ArrayList<>()));

        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) ->
        {
            request.getHeaders().add("Authorization", "Beer " + token);

            return execution.execute(request, body);
        }
        ));
    }

    @Test
    @Sql(scripts = { "classpath:InsertInitialBookRecordForTest.sql" })
    void shouldReturnBooksWhenBookAPICalled(){

        setUpHeader();

        BookDTO[] listOfBooks = restTemplate.getForObject("http://localhost:" + port + "/books", BookDTO[].class);

        assertThat(listOfBooks).isNotNull();

        assertThat(listOfBooks.length).isEqualTo(19);

    }

    @Test
    void shouldReturnBookDTOListWhenGetBooksIsCalled(){

        List<BookDTO> bookDTOs = new ArrayList<>();

        bookDTOs.add(getBookDTO());

        when(bookService.getBooks()).thenReturn(bookDTOs);

        ResponseEntity<List<BookDTO>> allBooks = bookController.getAllBooks();

        assertThat(allBooks).isNotNull();

        assertThat(Objects.requireNonNull(allBooks.getBody()).size()).isEqualTo(1);
    }

    @Test
    void shouldReturnBookDTOListWhenGetBooksByTitleIsCalled(){

        List<BookDTO> bookDTOs = new ArrayList<>();

        bookDTOs.add(getBookDTO());

        when(bookService.getBooksByTitle(anyString())).thenReturn(bookDTOs);

        ResponseEntity<List<BookDTO>> bookByTitle = bookController.getBookByTitle("HARRY POTTER TEST");

        assertThat(bookByTitle).isNotNull();

        assertThat(Objects.requireNonNull(bookByTitle.getBody()).size()).isEqualTo(1);
    }

    private BookDTO getBookDTO(){

        return BookDTO.builder()
                .title("Harry Potter TEST")
                .description("All your fantasies come true! TEST")
                .id(UUID.randomUUID())
                .releaseYear(2005).build();
    }

}