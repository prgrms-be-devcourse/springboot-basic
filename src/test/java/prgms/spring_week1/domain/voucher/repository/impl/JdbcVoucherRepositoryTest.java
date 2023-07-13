package prgms.spring_week1.domain.voucher.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import javax.sql.DataSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(2,jdbcVoucherRepository.findAll().size());
    }

    @Test
    @Order(2)
    void findByType() {
        List<Voucher> fixedVoucherList = jdbcVoucherRepository.findByType("FIXED");
        assertEquals(1,fixedVoucherList.size());
        List<Voucher> percentVoucherList = jdbcVoucherRepository.findByType("PERCENT");
        assertEquals(1,percentVoucherList.size());
    }

    @Test
    @Order(3)
    void delete() {
        jdbcVoucherRepository.delete();
        assertEquals(0,jdbcVoucherRepository.findAll().size());
    }
}
