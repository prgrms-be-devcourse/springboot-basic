package org.prgrms.devcourse.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.devcourse.voucher"})
    static class JdbcVoucherRepositoryTestConfig {

        @Bean
        public DataSource dataSource() {
            DataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcVoucherRepository(jdbcTemplate);
        }
    }

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    Voucher newVoucher;

    @BeforeAll
    void setup() {
        newVoucher = Voucher.of(UUID.randomUUID(), 90L, VoucherType.PERCENT_DISCOUNT_VOUCHER);
    }

    @BeforeEach
    void insertInitVoucher() {
        jdbcVoucherRepository.insert(newVoucher);
    }

    @AfterEach
    void clean() {
        jdbcVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("Insert를 정상적으로 할 수 있다.")
    void testInsertVoucher() {
        Voucher voucher = Voucher.of(UUID.randomUUID(), 2000L, VoucherType.FIXED_AMOUNT_DISCOUNT_VOUCHER);

        var insertVoucher = jdbcVoucherRepository.insert(voucher);

        assertThat(jdbcVoucherRepository.findAll(), hasSize(2));
    }

    @Test
    @DisplayName("Insert한 바우처를 조회할 수 있다.")
    void testReferInsertVoucher() {
        var findVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("같은 ID를 갖는 바우처를 Insert할 수 없다.")
    void testDuplicateIdVoucherInsert() {
        var findVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThrows(RuntimeException.class, () -> jdbcVoucherRepository.insert(Voucher.of(findVoucher.get().getVoucherId(), 50L, VoucherType.PERCENT_DISCOUNT_VOUCHER)));
    }

    @Test
    @DisplayName("정상적으로 Update가 된다.")
    void testUpdate() {
        var uuid = newVoucher.getVoucherId();
        Voucher updateVoucher = Voucher.of(uuid, 2000L, VoucherType.FIXED_AMOUNT_DISCOUNT_VOUCHER);

        jdbcVoucherRepository.update(updateVoucher);
        Voucher findVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId()).get();

        assertThat(findVoucher, samePropertyValuesAs(updateVoucher));
        assertThat(jdbcVoucherRepository.findAll(), hasSize(1));
    }

    @Test
    @DisplayName("모두 조회할 수 있다.")
    void testFindAll() {
        var voucher1 = Voucher.of(UUID.randomUUID(), 80L, VoucherType.PERCENT_DISCOUNT_VOUCHER);
        var voucher2 = Voucher.of(UUID.randomUUID(), 70L, VoucherType.PERCENT_DISCOUNT_VOUCHER);
        var voucher3 = Voucher.of(UUID.randomUUID(), 5000L, VoucherType.FIXED_AMOUNT_DISCOUNT_VOUCHER);
        var voucher4 = Voucher.of(UUID.randomUUID(), 7000L, VoucherType.FIXED_AMOUNT_DISCOUNT_VOUCHER);

        List<Voucher> allVoucher = jdbcVoucherRepository.findAll();
        assertThat(allVoucher, hasSize(1));
        jdbcVoucherRepository.insert(voucher1);
        jdbcVoucherRepository.insert(voucher2);
        jdbcVoucherRepository.insert(voucher3);
        jdbcVoucherRepository.insert(voucher4);
        allVoucher = jdbcVoucherRepository.findAll();

        assertThat(allVoucher, hasSize(5));
    }
}
