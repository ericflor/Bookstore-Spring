package com.example.SpringReactBookstore.integrationTEST;

import com.example.SpringReactBookstore.SpringReactBookstoreApplication;
import com.example.SpringReactBookstore.models.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = SpringReactBookstoreApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnBooksWhenBookAPICalled(){

        BookDTO[] bookList = testRestTemplate.getForObject("http://localhost:" + port + "/books", BookDTO[].class);

        assertThat(bookList).isNotNull();

        assertThat(bookList.length).isEqualTo(19);

    }

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnOneBookWhenCalledWithTestTitle(){

        BookDTO[] booksByTitle = testRestTemplate.getForObject("http://localhost:" + port + "/books/HARRY POTTER test", BookDTO[].class);

        assertThat(booksByTitle).isNotNull();

        assertThat(booksByTitle.length).isEqualTo(1);

    }
}
