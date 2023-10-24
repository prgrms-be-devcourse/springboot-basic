package com.zerozae.voucher.repository.voucher;

import com.zaxxer.hikari.HikariDataSource;
import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
public class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.zerozae.voucher.repository"}
    )
    static class testConfig {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(mysql.getJdbcUrl())
                    .username("root")
                    .password("test")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcVoucherRepository(jdbcTemplate);
        }
    }

    static JdbcDatabaseContainer mysql = new MySQLContainer("mysql:8.0.26")
            .withDatabaseName("test_mgmt")
            .withInitScript("schema.sql");

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    FixedDiscountVoucher fixedDiscountVoucher;
    PercentDiscountVoucher percentDiscountVoucher;

    @BeforeAll
    static void setUp(){
        mysql.start();
    }

    @AfterAll
    static void cleanUp(){
        mysql.stop();
    }

    @BeforeEach
    void setVoucher(){
        fixedDiscountVoucher = new FixedDiscountVoucher(UUID.randomUUID(), 10L, UseStatusType.AVAILABLE);
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L, UseStatusType.AVAILABLE);
    }

    @AfterEach
    void cleanRepository() {
        jdbcVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void saveVoucher_success_Test(){
        // Given

        // When
        Voucher savedVoucher = jdbcVoucherRepository.save(fixedDiscountVoucher);

        // Then
        assertThat(savedVoucher.getVoucherId(), is(fixedDiscountVoucher.getVoucherId()));
        assertThat(savedVoucher.getDiscount(), is(fixedDiscountVoucher.getDiscount()));
    }

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    void findAllVouchers_Success_Test(){
        // Given
        jdbcVoucherRepository.save(fixedDiscountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);

        // When
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        // Then
        assertThat(vouchers, containsInAnyOrder(
                hasProperty("voucherId", is(fixedDiscountVoucher.getVoucherId())),
                hasProperty("voucherId", is(percentDiscountVoucher.getVoucherId())))
        );
    }

    @Test
    @DisplayName("아이디로 바우처 조회 테스트")
    void findVoucherById_Success_Test(){
        // Given
        UUID voucherId = fixedDiscountVoucher.getVoucherId();
        jdbcVoucherRepository.save(fixedDiscountVoucher);

        // When
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucherId);

        // Then
        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), is(samePropertyValuesAs(fixedDiscountVoucher)));
    }

    @Test
    @DisplayName("존재하지 않는 바우처 아이디 Optional Emtpy 반환 테스트")
    void findVoucher_NotExist_Failed_Test(){
        // Given
        UUID notExistId = UUID.randomUUID();

        // When
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(notExistId);

        // Then
        assertTrue(findVoucher.isEmpty());
    }

    @Test
    @DisplayName("아이디로 바우처 삭제 테스트")
    void deleteById_Success_Test(){
        // Given
        jdbcVoucherRepository.save(fixedDiscountVoucher);

        // When
        jdbcVoucherRepository.deleteById(fixedDiscountVoucher.getVoucherId());

        // Then
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 전체 삭제 테스트")
    void deleteAllVouchers_Success_Test(){
        // Given
        jdbcVoucherRepository.save(fixedDiscountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);

        // When
        jdbcVoucherRepository.deleteAll();

        // Then
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 업데이트 테스트")
    void updateVoucher_Success_Test() {
        // Given
        jdbcVoucherRepository.save(fixedDiscountVoucher);
        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(20L, UseStatusType.UNAVAILABLE);

        // When
        jdbcVoucherRepository.update(fixedDiscountVoucher.getVoucherId(), voucherUpdateRequest);

        // Then
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedDiscountVoucher.getVoucherId());
        assertThat(voucher.get().getDiscount(), is(voucherUpdateRequest.getDiscount()));
        assertThat(voucher.get().getUseStatusType(), is(voucherUpdateRequest.getUseStatusType()));
    }
}