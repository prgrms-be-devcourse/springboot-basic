package prgms.spring_week1.domain.customer.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.events.CollectionEndEvent;
import prgms.spring_week1.domain.customer.repository.CustomerRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Optional;

@JdbcTest
class JdbcCustomerRepositoryTest {
    private CustomerRepository jdbcCustomerRepository;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        jdbcCustomerRepository = new JdbcCustomerRepository(dataSource);
    }

    @Test
    void findAll() {
        //when
        int result = jdbcCustomerRepository.findAll().size();
        //then
        assertThat(result ,is(2));
    }

    @Test
    void findByEmail() {
        assertThat(jdbcCustomerRepository.findByEmail("1sehan@naver.com").getName(),is("일세한"));
        Assertions.assertThrows(DataAccessException.class,() -> jdbcCustomerRepository.findByEmail("3sehan@naver.com"));
    }

    @Test
    void updateInfo() {
        //when
        jdbcCustomerRepository.updateInfo("1sehan@naver.com","3sehan@naver.com");
        //then
        assertThat(jdbcCustomerRepository.findByEmail("3sehan@naver.com").getName(),is("일세한"));
    }

    @Test
    void deleteByEmail() {
        //when
        jdbcCustomerRepository.deleteByEmail("1sehan@naver.com");
        //then
        assertThat(jdbcCustomerRepository.findAll().size(),is(1));
    }

    @Test
    void deleteAll() {
        //when
        jdbcCustomerRepository.deleteAll();
        //then
        assertThat(jdbcCustomerRepository.findAll(),is(Collections.emptyList()));
    }
}
