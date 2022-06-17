package com.example.SpringReactBookstore.repositories;

import com.example.SpringReactBookstore.models.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookDAO bookDAO;

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    void shouldBeAbleToFetchAllBooksInDb(){

        List<Book> all = bookDAO.findAll();

        Long totalBookCount = (long) all.size();

        assertEquals(totalBookCount, 2);

    }

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnOneBookWhenTitleIsTestTitle(){

        List<Book> harry_potter_test = bookDAO.findBooksByTitle("Harry Potter TEST");

        assertEquals(harry_potter_test.size(), 1);

    }
}
