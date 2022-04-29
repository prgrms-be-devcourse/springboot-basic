package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTemplateVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"me.programmers.springboot.basic.springbootbasic.voucher"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var datasource =  DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("programmers")
                    .password("programmers")
                    .type(HikariDataSource.class)
                    .build();
            datasource.setMaximumPoolSize(1000);
            datasource.setMinimumIdle(100);
            return datasource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplateVoucherRepository jdbcRepository;


    FixedAmountVoucher fixVoucher;
    PercentAmountVoucher percentVoucher;

    @BeforeAll
    void setup() {
        fixVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        percentVoucher = new PercentAmountVoucher(UUID.randomUUID(), 20);
    }

    @Test
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("전체 바우처를 조회할 수 있다.")
    public void testFindAll() {
        var vouchers = jdbcRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("FixVoucher 저장")
    public void testFixVoucherSave() {
        jdbcRepository.save(fixVoucher);

        Optional<Voucher> saveVoucher = jdbcRepository.findById(fixVoucher.getVoucherId());

        assertThat(saveVoucher.isEmpty(), is(false));
        assertThat(saveVoucher.get(), samePropertyValuesAs(fixVoucher));
    }

    @Test
    @DisplayName("FixVoucher 중복 저장시 예외 발생")
    public void testFixVoucherSaveDuplicate() {
        jdbcRepository.save(fixVoucher);
        assertThrows(DuplicateKeyException.class, () -> {
            jdbcRepository.save(fixVoucher);
        });
    }

    @Test
    @DisplayName("PercentVoucher 저장")
    public void testPercentVoucherSave() {
        jdbcRepository.save(percentVoucher);

        Optional<Voucher> saveVoucher = jdbcRepository.findById(percentVoucher.getVoucherId());

        assertThat(saveVoucher.isEmpty(), is(false));
        assertThat(saveVoucher.get(), samePropertyValuesAs(percentVoucher));
    }

    @Test
    @DisplayName("PercentVoucher 중복 저장시 예외 발생")
    public void testPercentVoucherSaveDuplicate() {
        jdbcRepository.save(percentVoucher);
        assertThrows(DuplicateKeyException.class, () -> {
            jdbcRepository.save(percentVoucher);
        });
    }

    @Test
    @DisplayName("FixVoucher 수정 성공")
    public void testFixVoucherUpdate() {
        FixedAmountVoucher voucher = (FixedAmountVoucher) jdbcRepository.findById(UUID.fromString("13fc058b-5e0c-451b-9ca8-0e9da5f317a5")).get();
        fixVoucher.setAmount(3000);
        jdbcRepository.update(voucher);

        FixedAmountVoucher updatedVoucher = (FixedAmountVoucher) jdbcRepository.findById(UUID.fromString("13fc058b-5e0c-451b-9ca8-0e9da5f317a5")).get();
        assertThat(voucher.getAmount(), is(updatedVoucher.getAmount()));
    }

    @Test
    @DisplayName("FixVoucher가 없을 때 수정 실패")
    public void testFixVoucherUpdateFail() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucher.setAmount(3000);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcRepository.update(voucher);
        });
    }

}