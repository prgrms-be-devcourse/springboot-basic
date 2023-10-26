package com.programmers.vouchermanagement.voucher.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.zaxxer.hikari.HikariDataSource;

@Testcontainers(disabledWithoutDocker = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withUsername("test")
            .withPassword("1234")
            .withInitScript("init.sql");

    DataSource dataSource;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    VoucherRepository voucherRepository;

    @BeforeAll
    void setUp() {
        dataSource = DataSourceBuilder.create()
                .url(mySQLContainer.getJdbcUrl())
                .username(mySQLContainer.getUsername())
                .password(mySQLContainer.getPassword())
                .type(HikariDataSource.class)
                .build();

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        voucherRepository = new JdbcVoucherRepository(namedParameterJdbcTemplate);
    }

    @Test
    @Order(1)
    @DisplayName("테스트 컨테이너 생성을 성공한다.")
    void testMySQLContainerRunning() {
        assertThat(mySQLContainer.isRunning(), is(true));
        assertThat(mySQLContainer.getUsername(), is("test"));
    }

    @Test
    @Order(2)
    @DisplayName("JdbcVoucherRepository 템플릿을 주입 받아 생성된다.")
    void testJdbcVoucherRepositoryCreation() {
        assertThat(voucherRepository, notNullValue());
    }

    @Test
    @Order(3)
    @DisplayName("저장된 바우처가 없을 시 빈 리스트를 반환한다.")
    void testFindAllVouchersSuccessful_ReturnEmptyList() {
        //when
        final List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @Order(4)
    @DisplayName("바우처 생성을 성공한다")
    void testVoucherCreationSuccessful() {
        //given
        final Voucher fixedVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);

        //when
        final Voucher savedVoucher = voucherRepository.save(fixedVoucher);

        //then
        assertThat(savedVoucher, samePropertyValuesAs(fixedVoucher));
    }

    @Test
    @Order(5)
    @DisplayName("존재하지 않는 아이디 조회를 실패한다.")
    void testFindVoucherByIdFailed() {
        //when
        final Optional<Voucher> voucher = voucherRepository.findById(UUID.randomUUID());

        //then
        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("바우처 아이디 조회를 성공한다.")
    void testFindVoucherByIdSuccessful_ReturnList() {
        //given
        final Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT);
        voucherRepository.save(voucher);

        //when
        final Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        //then
        assertThat(foundVoucher.isEmpty(), is(false));
        assertThat(foundVoucher.get(), samePropertyValuesAs(voucher));
    }
}
