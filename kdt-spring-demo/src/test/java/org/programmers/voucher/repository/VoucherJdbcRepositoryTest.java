package org.programmers.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.voucher.model.Voucher;
import org.programmers.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.voucher.repository"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/homework")
                    .username("root")
                    .password("skyey9808")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public VoucherJdbcRepository voucherJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new VoucherJdbcRepository(namedParameterJdbcTemplate);
        }
    }

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    Voucher voucher;

    @BeforeEach
    void setUp() {
        voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, 1L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        voucherJdbcRepository.save(voucher);
    }

    @AfterEach
    void clearUp() {
        voucherJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("FIXED 바우처를 저장할 수 있다.")
    @Order(1)
    void fixedVoucherSave() {
        Voucher testVoucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, 10L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        voucherJdbcRepository.save(testVoucher);

        Optional<Voucher> findVoucherById = voucherJdbcRepository.findById(testVoucher.getVoucherId());
        assertThat(findVoucherById.get(), samePropertyValuesAs(testVoucher));
    }

    @Test
    @DisplayName("PERCENT 바우처를 저장할 수 있다.")
    @Order(2)
    void percentVoucherSave() {
        Voucher testVoucher = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, 10L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        voucherJdbcRepository.save(testVoucher);

        Optional<Voucher> findVoucherById = voucherJdbcRepository.findById(testVoucher.getVoucherId());
        assertThat(findVoucherById.get(), samePropertyValuesAs(testVoucher));
    }

    @Test
    @DisplayName("바우처를 저장하지 못하면, 예외가 발생한다.")
    @Order(3)
    void saveException() {
        Voucher testVoucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, 10000000L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.save(testVoucher));
    }

    @Test
    @DisplayName("바우처의 값을 수정할 수 있다.")
    @Order(4)
    void update() {
        long changeValue = 99L;
        voucherJdbcRepository.update(VoucherType.FIXED, 1L, changeValue);

        Optional<Voucher> findVoucherById = voucherJdbcRepository.findById(voucher.getVoucherId());
        assertThat(findVoucherById.get().getVoucherValue(), is(99L));
    }

    @Test
    @DisplayName("바우처를 수정하지 못하면 예외가 발생한다.")
    @Order(5)
    void updateException() {
        long changeValue = 123456789L;
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.update(voucher.getVoucherType(), voucher.getVoucherValue(), changeValue));
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.update(VoucherType.valueOf("fix"), voucher.getVoucherValue(), 10L));
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.update(VoucherType.FIXED, 2L, 10L));
    }

    @Test
    @DisplayName("바우처 전체를 조회할 수 있다.")
    @Order(6)
    void findAll() {
        List<Voucher> all = voucherJdbcRepository.findAll();
        assertThat(all.isEmpty(), is(false));
        assertThat(all, hasSize(1));
    }

    @Test
    @DisplayName("아이디로 바우처를 조회할 수 있다.")
    @Order(7)
    void findById() {
        Optional<Voucher> findVoucherbyId = voucherJdbcRepository.findById(voucher.getVoucherId());
        assertEquals(findVoucherbyId.get().getVoucherId(), voucher.getVoucherId());
    }

    @Test
    @DisplayName("아이디로 바우처를 조회하지 못하면 예외가 발생한다.")
    @Order(8)
    void findByIdException() {
        assertThrows(EmptyResultDataAccessException.class, () -> voucherJdbcRepository.findById(UUID.randomUUID()).get());
    }

    @Test
    @DisplayName("바우처 타입과 값으로 바우처를 조회할 수 있다.")
    @Order(9)
    void findByVoucherTypeAndVoucherValue() {
        Optional<Voucher> findVoucherByVoucherTypeAndVoucherValue = voucherJdbcRepository.findByVoucherTypeAndVoucherValue(VoucherType.FIXED, 1L);
        assertThat(findVoucherByVoucherTypeAndVoucherValue.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("바우처 타입과 값으로 바우처를 찾지못해서 값을 사용할 수 없으면 예외가 발생한다.")
    @Order(10)
    void findByVoucherTypeAndVoucherValueException() {
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.findByVoucherTypeAndVoucherValue(VoucherType.getInputType("fix"), 1L));
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.findByVoucherTypeAndVoucherValue(VoucherType.getInputType("fixed"), 123456789L));
    }

    @Test
    @DisplayName("바우처 아이디로 바우처를 삭제할 수 있다.")
    @Order(11)
    void deleteByVoucherId() {
        Voucher testVoucher = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, 10L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        voucherJdbcRepository.save(testVoucher);
        List<Voucher> before = voucherJdbcRepository.findAll();
        assertThat(before, hasSize(2));

        voucherJdbcRepository.deleteByVoucherId(testVoucher.getVoucherId());
        List<Voucher> after = voucherJdbcRepository.findAll();
        assertThat(after, hasSize(1));
    }

    @Test
    @DisplayName("바우처 아이디로 회원을 삭제하지 못하면 예외가 발생한다.")
    @Order(12)
    void deleteByVoucherIdException() {
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.deleteByVoucherId(UUID.randomUUID()));
    }

    @Test
    @DisplayName("바우처타입과 값으로 삭제할 수 있다.")
    @Order(13)
    void deleteByVoucherTypeAndValue() {
        Voucher testVoucher = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, 10L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        voucherJdbcRepository.save(testVoucher);

        voucherJdbcRepository.deleteByVoucherTypeAndValue(VoucherType.PERCENT, 10L);
    }

    @Test
    @DisplayName("바우처타입과 값을 삭제하지 못할 경우 예외가 발생한다.")
    @Order(14)
    void deleteByVoucherTypeAndValueException() {
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.deleteByVoucherTypeAndValue(VoucherType.valueOf("fix"), 1L));
        assertThrows(RuntimeException.class, () -> voucherJdbcRepository.deleteByVoucherTypeAndValue(VoucherType.FIXED, 12L));
    }

    @Test
    @DisplayName("바우처 모두를 삭제할 수 있다.")
    @Order(15)
    void deleteAll() {
        List<Voucher> before = voucherJdbcRepository.findAll();
        assertThat(before, hasSize(1));

        voucherJdbcRepository.deleteAll();
        List<Voucher> after = voucherJdbcRepository.findAll();
        assertThat(after, hasSize(0));
    }
}
