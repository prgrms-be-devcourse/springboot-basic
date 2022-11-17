package com.programmers.kwonjoosung.springbootbasicjoosung.repository;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.JdbcVoucherRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Transactional
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = "com.programmers.kwonjoosung.springbootbasicjoosung.repository")
    static class Config {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("vouchers-schema.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeAll
    void setUp() {
        this.jdbcVoucherRepository = new JdbcVoucherRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void insertTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(),1000);
        //when
        jdbcVoucherRepository.insert(voucher);
        //then
        assertEquals(voucher,jdbcVoucherRepository.findById(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    @Disabled
    void findAllTest() {
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(),1000);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(),10);
        jdbcVoucherRepository.insert(voucher1);
        jdbcVoucherRepository.insert(voucher2);
        //when
        List<Voucher> voucherList = jdbcVoucherRepository.findAll();
        //then
        assertEquals(voucher1,voucherList.get(0));
        assertEquals(voucher2,voucherList.get(1));
    }

    @Test
    @DisplayName("바우처를 업데이트 테스트")
    void updateTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(),1000);
        jdbcVoucherRepository.insert(voucher);
        Voucher newVoucher = VoucherFactory.createVoucher(VoucherType.PERCENT, voucher.getVoucherId(),10);
        //when
        jdbcVoucherRepository.update(newVoucher);
        //then
        assertEquals(newVoucher,jdbcVoucherRepository.findById(voucher.getVoucherId()));
    }


    @Test
    @DisplayName("바우처 삭제 테스트")
    void deleteTest() {
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(),1000);
        jdbcVoucherRepository.insert(voucher);
        //when
        jdbcVoucherRepository.deleteById(voucher.getVoucherId());
        //then
        assertThrows(EmptyResultDataAccessException.class,() -> jdbcVoucherRepository.findById(voucher.getVoucherId()));
    }

}