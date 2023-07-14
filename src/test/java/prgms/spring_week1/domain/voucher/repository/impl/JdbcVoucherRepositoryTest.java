package prgms.spring_week1.domain.voucher.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;

import javax.sql.DataSource;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


@JdbcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {
    private VoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        jdbcVoucherRepository = new JdbcVoucherRepository(dataSource);
    }

    @Test
    @Order(1)
    void findAll_before() {
        assertThat(jdbcVoucherRepository.findAll(),hasSize(2));
    }

    @Test
    @Order(2)
    void findByType() {
        List<Voucher> fixedVoucherList = jdbcVoucherRepository.findByType("FIXED");
        assertThat(fixedVoucherList,hasSize(1));
        List<Voucher> percentVoucherList = jdbcVoucherRepository.findByType("PERCENT");
        assertThat(percentVoucherList,hasSize(1));
    }

    @Test
    @Order(3)
    void delete() {
        jdbcVoucherRepository.delete();
        assertThat(jdbcVoucherRepository.findAll(),hasSize(0));
    }
}
