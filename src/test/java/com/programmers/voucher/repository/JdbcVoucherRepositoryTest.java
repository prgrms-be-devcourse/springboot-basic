package com.programmers.voucher.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.voucher.repository"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_test_db")
                    .username("admin")
                    .password("admin1234")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private DataSource dataSource;

    private JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeEach
    void setUp() {
        jdbcVoucherRepository = new JdbcVoucherRepository(dataSource);
    }

    @AfterEach
    void after() {
        jdbcVoucherRepository.deleteAll();
    }

    @DisplayName("바우처를 저장한다")
    @Test
    void save() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);

        //when
        Voucher result = jdbcVoucherRepository.save(fixedAmountVoucher);

        //then
        assertThat(result.getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
    }

    @DisplayName("저장된 바우처들을 모두 조회한다")
    @Test
    void findAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);

        //when
        List<Voucher> result = jdbcVoucherRepository.findAll();

        //then
        assertThat(result.size(), is(2));
    }

    @DisplayName("바우처를 id로 조회한다")
    @Test
    void findById() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);
        jdbcVoucherRepository.save(fixedAmountVoucher);

        //when
        Optional<Voucher> result = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(result.get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
    }

    @DisplayName("바우처를 id로 조회했을 때 존재하지 않으면 예외처리한다")
    @Test
    void findByIdException() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);

        //when
        //then
        assertThatThrownBy(() -> jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("바우처를 수정한다")
    @Test
    void update() {
        //given
        UUID id = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(id, "testName", 10L);
        jdbcVoucherRepository.save(fixedAmountVoucher);

        //when
        Voucher result = jdbcVoucherRepository.update(new FixedAmountVoucher(id, "update", 20L));

        //then
        assertThat(result.getVoucherId(), is(id));
        assertThat(result.getVoucherName(), is("update"));
        assertThat(result.getVoucherValue(), is(20L));
    }

    @DisplayName("id로 바우처를 삭제한다")
    @Test
    void deleteById() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);
        jdbcVoucherRepository.save(fixedAmountVoucher);

        //when
        jdbcVoucherRepository.deleteById(fixedAmountVoucher.getVoucherId());
        List<Voucher> result = jdbcVoucherRepository.findAll();

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("저장된 모든 바우처들을 삭제한다")
    @Test
    void deleteAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);

        //when
        jdbcVoucherRepository.deleteAll();
        List<Voucher> result = jdbcVoucherRepository.findAll();

        //then
        assertThat(result).isEmpty();
    }
}