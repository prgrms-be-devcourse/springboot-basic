package org.prgrms.vouchermanagement.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.prgrms.vouchermanagement.voucher.domain.dto.VoucherCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;
    @Autowired
    CustomerRepository jdbcCustomerRepository;
    @Autowired
    DataSource dataSource;
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-customer", ScriptResolver.classPathScript("schema/create_table_ddl.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @AfterEach
    void clear() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    @Test
    @DisplayName("바우처 저장 성공")
    void saveVoucher() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.createNormalCustomer(customerId, "name", "email@google.com", LocalDateTime.now());
        Voucher fixedAmountVoucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 100, customerId);

        // when
        jdbcCustomerRepository.save(customer);
        jdbcVoucherRepository.save(fixedAmountVoucher);

        // then
        Voucher savedVoucher = jdbcVoucherRepository.findAll().get(0);

        assertThat(fixedAmountVoucher)
                .usingRecursiveComparison()
                .isEqualTo(savedVoucher);
    }

    @Test
    @DisplayName("바우처 ID로 찾기 성공")
    void findVoucherById() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.createNormalCustomer(customerId, "name", "email@google.com", LocalDateTime.now());
        Voucher voucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, customerId);
        Voucher anotherVoucher = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, customerId);

        jdbcCustomerRepository.save(customer);
        jdbcVoucherRepository.save(voucher);
        jdbcVoucherRepository.save(anotherVoucher);

        // when
        Voucher findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId()).get();

        // then
        assertThat(voucher).usingRecursiveComparison()
                .isEqualTo(findVoucher);
    }

    @Test
    @DisplayName("찾는 바우처 ID가 DB에 없는 경우")
    void findVoucherIdNotExist() {

        // given
        Voucher anotherVoucher = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, UUID.randomUUID());

        // when
        Optional<Voucher> findVoucher = jdbcVoucherRepository.findById(anotherVoucher.getVoucherId());

        // then
        assertThat(findVoucher).isEmpty();
    }

    @Test
    @DisplayName("모든 바우처 조회 성공")
    void findAllVouchers() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.createNormalCustomer(customerId, "name", "email@google.com", LocalDateTime.now());

        Voucher voucher1 = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, customerId);
        Voucher voucher2 = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, customerId);

        jdbcCustomerRepository.save(customer);
        jdbcVoucherRepository.save(voucher1);
        jdbcVoucherRepository.save(voucher2);

        // when
        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();

        // then
        assertThat(allVouchers).hasSize(2);
        assertThat(allVouchers)
                .extracting("voucherId", "discountAmount", "voucherType", "customerId")
                .contains(tuple(voucher1.getVoucherId(), voucher1.getDiscountAmount(), voucher1.getVoucherType(), voucher1.getCustomerId()))
                .contains(tuple(voucher2.getVoucherId(), voucher2.getDiscountAmount(), voucher2.getVoucherType(), voucher2.getCustomerId()));
    }

    @Test
    @DisplayName("DB에 데이터가 존재하지 않는 경우 모든 바우처 조회 성공")
    void findAllVouchersNotExist() {

        // when
        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();

        // then
        assertThat(allVouchers).isEmpty();
    }

    @Test
    @DisplayName("DB 데이터 전부 삭제")
    void deleteAll() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.createNormalCustomer(customerId, "name", "email@google.com", LocalDateTime.now());
        Voucher voucher1 = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 1000, customerId);
        Voucher voucher2 = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 10, customerId);

        jdbcCustomerRepository.save(customer);
        jdbcVoucherRepository.save(voucher1);
        jdbcVoucherRepository.save(voucher2);

        // when
        jdbcVoucherRepository.clear();

        // then
        List<Voucher> allVouchers = jdbcVoucherRepository.findAll();
        assertThat(allVouchers).isEmpty();
    }

    @Test
    @DisplayName("CustomerId로 바우처 찾기")
    void findVoucherByCustomerId() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.createNormalCustomer(customerId, "name", "email@google.com", LocalDateTime.now());
        Voucher fixedAmountVoucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 10, customerId);
        Voucher percentDiscountVoucher = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 1000, customerId);
        jdbcCustomerRepository.save(customer);
        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);

        // when
        List<Voucher> vouchers = jdbcVoucherRepository.findVouchersByCustomerId(customerId);

        // then
        assertThat(vouchers).hasSize(2);
        assertThat(vouchers)
                .extracting("voucherId", "discountAmount", "voucherType", "customerId")
                .contains(tuple(fixedAmountVoucher.getVoucherId(), fixedAmountVoucher.getDiscountAmount(), fixedAmountVoucher.getVoucherType(), fixedAmountVoucher.getCustomerId()))
                .contains(tuple(percentDiscountVoucher.getVoucherId(), percentDiscountVoucher.getDiscountAmount(), percentDiscountVoucher.getVoucherType(), percentDiscountVoucher.getCustomerId()));
    }

    @Test
    @DisplayName("CustomerId로 바우처 삭제")
    void deleteVoucherByCustomerId() {
        // given
        UUID customerId = UUID.randomUUID();
        UUID anotherCustomerId = UUID.randomUUID();
        Customer customer = Customer.createNormalCustomer(customerId, "name", "email@google.com", LocalDateTime.now());
        Customer anotherCustomer = Customer.createNormalCustomer(anotherCustomerId, "name", "anotherEmail@google.com", LocalDateTime.now());
        Voucher fixedAmountVoucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 10, customerId);
        Voucher percentDiscountVoucher = createVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 1000, customerId);
        Voucher anotherVoucher = createVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000, anotherCustomerId);

        jdbcCustomerRepository.save(customer);
        jdbcCustomerRepository.save(anotherCustomer);

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);
        jdbcVoucherRepository.save(anotherVoucher);

        // when
        jdbcVoucherRepository.deleteVoucherByCustomerId(customerId);
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        // then
        assertThat(vouchers).hasSize(1);
        assertThat(vouchers)
                .extracting("voucherId", "discountAmount", "voucherType", "customerId")
                .contains(tuple(anotherVoucher.getVoucherId(), anotherVoucher.getDiscountAmount(), anotherVoucher.getVoucherType(), anotherVoucher.getCustomerId()));

    }

    private Voucher createVoucher(UUID uuid, VoucherType voucherType, int discountAmount, UUID customerId) {
        return VoucherType.createVoucher(VoucherCreateDTO.of(uuid, voucherType.name(), discountAmount, customerId));
    }

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.vouchermanagement.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-customer")
                    .username("test")
                    .password("test")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }
    }

}