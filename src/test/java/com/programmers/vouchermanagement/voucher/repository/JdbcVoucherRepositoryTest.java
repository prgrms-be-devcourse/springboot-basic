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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.zaxxer.hikari.HikariDataSource;

@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
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
    @DisplayName("테스트 컨테이너 생성을 성공한다.")
    void testMySQLContainerRunning() {
        assertThat(mySQLContainer.isRunning(), is(true));
        assertThat(mySQLContainer.getUsername(), is("test"));
    }

    @Test
    @DisplayName("JdbcVoucherRepository가 템플릿을 주입 받아 생성된다.")
    void testJdbcVoucherRepositoryCreation() {
        assertThat(voucherRepository, notNullValue());
    }

    @Test
    @DisplayName("저장된 바우처가 없을 시 빈 리스트를 반환한다.")
    void testFindAllVouchersSuccessful_ReturnEmptyList() {
        //given
        voucherRepository.deleteAll();

        //when
        List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 생성을 성공한다")
    void testVoucherCreationSuccessful() {
        //given
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);

        //when
        Voucher savedVoucher = voucherRepository.save(fixedVoucher);

        //then
        assertThat(savedVoucher, samePropertyValuesAs(fixedVoucher));
    }

    @Test
    @DisplayName("존재하지 않는 아이디 조회를 실패한다.")
    void testFindVoucherByIdFailed() {
        //when
        Optional<Voucher> voucher = voucherRepository.findById(UUID.randomUUID());

        //then
        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 아이디 조회를 성공한다.")
    void testFindVoucherByIdSuccessful_ReturnList() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT);
        voucherRepository.save(voucher);

        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        //then
        assertThat(foundVoucher.isEmpty(), is(false));
        assertThat(foundVoucher.get().getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처 전체 조회를 성공한다.")
    void testFindAllVouchersSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(5000), VoucherType.FIXED);
        voucherRepository.save(voucher);

        //when
        List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers, hasSize(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("바우처 정보 수정을 성공한다.")
    void testVoucherUpdateSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);
        Voucher foundVoucher = voucherRepository.findById(voucher.getVoucherId()).get();

        //when
        Voucher updatedVoucher = new Voucher(voucher.getVoucherId(), new BigDecimal(5000), voucher.getVoucherType());
        voucherRepository.save(updatedVoucher);
        Voucher newlyFoundVoucher = voucherRepository.findById(foundVoucher.getVoucherId()).get();

        //then
        assertThat(newlyFoundVoucher.getDiscountValue(), is(updatedVoucher.getDiscountValue()));
    }

    @Test
    @DisplayName("바우처 삭제를 성공한다.")
    void testDeleteVoucherSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(50), VoucherType.PERCENT);
        voucherRepository.save(voucher);

        //when
        voucherRepository.deleteById(voucher.getVoucherId());

        //then
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(foundVoucher.isEmpty(), is(true));
    }
}
