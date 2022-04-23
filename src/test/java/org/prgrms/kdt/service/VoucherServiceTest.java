package org.prgrms.kdt.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.*;
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
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.repository", "org.prgrms.kdt.service"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order-mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherService voucherService;

    @BeforeAll
    void clean() {
        var mysqldConfig = aMysqldConfig(Version.v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterEach
    void cleanTable() {
        jdbcVoucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @DisplayName("유효한 voucherType으로만 바우처를 생성할 수 있다.")
    @Test
    void createVoucherByValidateVoucherType() {
        assertAll(
                () -> assertThrows(Exception.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository).createVoucher(UUID.randomUUID(), 3, 100)),
                () -> assertThat(new VoucherService(new MemoryVoucherRepository(), customerRepository).createVoucher(UUID.randomUUID(), 1, 100).getClass(), is(FixedAmountVoucher.class)),
                () -> assertThat(new VoucherService(new MemoryVoucherRepository(), customerRepository).createVoucher(UUID.randomUUID(), 2, 100).getClass(), is(PercentDiscountVoucher.class))
        );
    }

    @DisplayName("유효한 discountAmount로만 생성 할 수 있다.")
    @Test
    void createVoucherByValidateDiscountAmount() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository).createVoucher(UUID.randomUUID(), 1, -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(),customerRepository).createVoucher(UUID.randomUUID(), 1, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository).createVoucher(UUID.randomUUID(), 2, -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository).createVoucher(UUID.randomUUID(), 2, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository).createVoucher(UUID.randomUUID(), 2, 110))
        );
    }


    @DisplayName("바우처가 생성되어야 한다.")
    @Test
    void createVoucher() {
        VoucherService voucherService = new VoucherService(new MemoryVoucherRepository(), customerRepository);

        Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), 1, 100);

        assertThat(voucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(voucher.getDiscountAmount(), is(100L));
        assertThat(voucher, notNullValue());
    }

    @DisplayName("바우처가 생성되어야 한다. (mock) - voucher : null")
    @Test
    @Disabled
    void createVoucherByMock() {
        VoucherService voucherService = mock(VoucherService.class);

        Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), 1, 100);

        assertThat(voucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(voucher.getDiscountAmount(), is(100L));
        assertThat(voucher, notNullValue());
    }

    @DisplayName("바우처 리스트가 출력되어야 한다.")
    @Test
    void printVoucherList() {
        VoucherRepository voucherRepository = mock(MemoryVoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository, customerRepository);

        voucherService.createVoucher(UUID.randomUUID(), 1, 100);
        voucherService.getVoucherList();

        verify(voucherRepository).getVoucherList();
    }

    @DisplayName("등록된 바우처가 없으면 바우처리스트가 출력되지 않는다.")
    @Test
    void printEmptyVoucherList() {
        VoucherRepository voucherRepository = mock(MemoryVoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository, customerRepository);

        voucherService.getVoucherList();

        assertThat((Map<UUID, Voucher>) voucherRepository.getVoucherList(), anEmptyMap());
    }

    @DisplayName("바우처를 고객에게 할당 할 수 있다.")
    @Test
    void provideVoucherToCustomer() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);
        jdbcVoucherRepository.insert(voucher);

        Optional<Voucher> returnVoucher = voucherService.provideVoucherToCustomer(voucher.getVoucherId().toString(), customer.getCustomerId().toString());

        assertThat(returnVoucher.get().getVoucherId(), equalTo(voucher.getVoucherId()));
    }

    @DisplayName("voucherId가 유효하지 않으면 고객에게 바우처를 할당 할 수 없다.")
    @Test
    void validateVoucherIdWhenProvide() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);
        Optional<Voucher> returnVoucher = voucherService.provideVoucherToCustomer(UUID.randomUUID().toString(), customer.getCustomerId().toString());

        assertThat(returnVoucher.isEmpty(), is(true));
    }

    @DisplayName("customerId가 유효하지 않으면 고객에게 바우처를 할당 할 수 없다.")
    @Test
    void validateCustomerIdWhenProvide() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        jdbcVoucherRepository.insert(voucher);

        Optional<Voucher> returnVoucher = voucherService.provideVoucherToCustomer(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertThat(returnVoucher.isEmpty(), is(true));
    }

    @DisplayName("이미 할당되어있는 바우처는 고객에게 재할당 할 수 없다.")
    @Test
    void validateReprovide() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insert(customer);
        jdbcVoucherRepository.insert(voucher);
        jdbcVoucherRepository.updateVoucherOwner(voucher.getVoucherId(), customer.getCustomerId());

        Optional<Voucher> returnVoucher = voucherService.provideVoucherToCustomer(voucher.getVoucherId().toString(), customer.getCustomerId().toString());
        assertThat(returnVoucher.isEmpty(), is(true));
    }

}