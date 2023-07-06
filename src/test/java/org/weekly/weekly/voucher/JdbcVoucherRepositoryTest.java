package org.weekly.weekly.voucher;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.repository.JdbcVoucherRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class JdbcVoucherRepositoryTest {
    @Container
    static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:8")
            .withInitScript("schema.sql");

    @DynamicPropertySource
    public static void overrideContainerProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        System.out.println(mySQLContainer.getJdbcUrl());
        System.out.println(mySQLContainer.getHost());
        System.out.println(mySQLContainer.getUsername());
        System.out.println(mySQLContainer.getPassword());
        System.out.println(mySQLContainer.getDriverClassName());
        System.out.println(mySQLContainer.getContainerName());
        System.out.println(mySQLContainer.getDatabaseName());
        dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username",mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password",mySQLContainer::getPassword);
    }

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;


    static Voucher fixedVoucher;
    static Voucher percentVoucher;

    @BeforeEach
    void setUp() {
        fixedVoucher = Voucher.of(UUID.randomUUID(), 10000, LocalDate.now(), 7, DiscountType.FIXED);
        percentVoucher = Voucher.of(UUID.randomUUID(), 50, LocalDate.now(), 7, DiscountType.PERCENT);
    }

    @Test
    @Order(1)
    void 전체_바우처_검색_테스트() {
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));

        vouchers.stream().forEach(voucher -> System.out.println(voucher.getDiscountType().name()));
    }

    @Test
    @Order(1)
    void 아이디를_통한_검색_실패_테스트() {
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    @Order(1)
    void 아이디를_통한_검색_성공_테스트() {
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());
        assertThat(voucher.isPresent(), is(false));
    }

    @Test
    @Order(1)
    void 할인정책을_통한_검색_테스트() {
        List<Voucher> vouchers = jdbcVoucherRepository.findByDiscountType(percentVoucher.getDiscountType());

        assertThat(vouchers.isEmpty(), is(false));

        vouchers.stream()
                .forEach(voucher -> assertThat(voucher.getDiscountType(), is(DiscountType.PERCENT)));
    }

    @Test
    @Order(2)
    void 할인바우처_등록성공_테스트() {
        Voucher voucher = jdbcVoucherRepository.insert(percentVoucher);

        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(percentVoucher.getVoucherId());

        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @Order(2)
    void 고정바우처_등록성공_테스트() {
        Voucher voucher = jdbcVoucherRepository.insert(fixedVoucher);

        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());

        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(voucher));
    }


    @ParameterizedTest
    @CsvSource({"5000, 0", "15000, 5000"})
    @Order(3)
    void 고정바우처_정보_업데이트_테스트(int amount, long reaminExpected) {
        // Given
        jdbcVoucherRepository.insert(fixedVoucher);

        // When
        long remain = fixedVoucher.applyDiscount(amount);
        jdbcVoucherRepository.update(fixedVoucher);
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(fixedVoucher.getVoucherId());

        // Then
        assertThat(remain, is(reaminExpected));
        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get(), samePropertyValuesAs(fixedVoucher));
    }

    @ParameterizedTest
    @CsvSource({"3000, 1500", "1000, 500"})
    @Order(3)
    void 퍼센트바우처_정보_업데이트_테스트(int amount, long reaminExpected) {
        // Given
        jdbcVoucherRepository.insert(percentVoucher);

        // When
        long remain = percentVoucher.applyDiscount(amount);
        jdbcVoucherRepository.update(percentVoucher);
        Optional<Voucher> voucher = jdbcVoucherRepository.findById(percentVoucher.getVoucherId());

        // Then
        assertThat(remain, is(reaminExpected));
        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get(), samePropertyValuesAs(percentVoucher));
    }

    @Test
    @Order(4)
    void 바우처_삭제_테스트() {
        // Given
        Voucher voucher = jdbcVoucherRepository.insert(percentVoucher);
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(findVoucher.isPresent(), is(true));

        // when
        jdbcVoucherRepository.deleteById(voucher.getVoucherId());

        // Then
        Optional<Voucher> deleteVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(deleteVoucher.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    void 전체_바우처_삭제_테스트() {
        // Given
        jdbcVoucherRepository.deleteAll();

        // when
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }
}
