package org.prgrms.kdt.domain.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.repository.JdbcCustomerRepository;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt"}
    )
    static class config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher")
                    .username("park")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    JdbcCustomerRepository customerRepository;

    @BeforeEach
    void cleanData() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void save() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);

        UUID savedId = voucherRepository.save(voucher);

        assertThat(voucherId).isEqualTo(savedId);
    }

    @Test
    void findById() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);

        voucherRepository.save(voucher);
        Optional<Voucher> findVoucher = voucherRepository.findById(voucherId);

        assertThat(findVoucher.get()).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    void findAll() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);

        voucherRepository.save(voucher);
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers.size()).isEqualTo(1);
    }

    @Test
    public void findByCustomerId() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        customerRepository.save(new Customer(customerId, "park", "d@naver.com", now, now));
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, customerId, now, now);
        voucherRepository.save(voucher);
        //when
        List<Voucher> findVouchers = voucherRepository.findByCustomerId(customerId);
        //then
        assertThat(findVouchers.size()).isEqualTo(1);
        assertThat(findVouchers.get(0)).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    void updateById() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);
        Voucher updateVoucher = new Voucher(voucherId, VoucherType.FIXED_AMOUNT, 20000L, now, now);

        int updateCnt = voucherRepository.updateById(updateVoucher);

        assertThat(updateCnt).isEqualTo(1);
    }

    @Test
    void deleteById() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);

        voucherRepository.deleteById(voucherId);
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers).isEmpty();
    }

    @Test
    void deleteAll() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);

        voucherRepository.deleteAll();
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers).isEmpty();
    }

    @Test
    void deleteByCustomerId() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        customerRepository.save(new Customer(customerId, "park", "d@naver.com", now, now));
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, customerId, now, now);
        voucherRepository.save(voucher);

        voucherRepository.deleteByCustomerId(customerId);
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers).isEmpty();
    }
}