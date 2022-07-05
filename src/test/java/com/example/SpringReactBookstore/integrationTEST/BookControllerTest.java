package com.example.SpringReactBookstore.integrationTEST;

import com.example.SpringReactBookstore.SpringReactBookstoreApplication;
import com.example.SpringReactBookstore.config.JwtUtil;
import com.example.SpringReactBookstore.models.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = SpringReactBookstoreApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnOneBookWhenCalledWithTestTitle(){

        setUpHeader();

        BookDTO[] booksByTitle = restTemplate.getForObject("http://localhost:" + port + "/books/HARRY POTTER test", BookDTO[].class);

        assertThat(booksByTitle).isNotNull();

        assertThat(booksByTitle.length).isEqualTo(1);

    }
}
