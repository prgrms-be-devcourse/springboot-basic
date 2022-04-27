package org.prgms.kdtspringvoucher.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.prgms.kdtspringvoucher.voucher.domain.FixedAmountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan
    static class testConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/test_db")
                    .username("root")
                    .password("a123456789")
                    .type(HikariDataSource.class)
                    .build();
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
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }
    }

    @Autowired
    VoucherRepository voucherRepository;

    @AfterEach
    void cleanup() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("customerId를 제외한 Voucher를 저장할 수 있어야 한다.")
    void insertNoCustomerIdTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 1000L, VoucherType.FIXED, LocalDateTime.now());
        //when
        Voucher saveVoucher = voucherRepository.save(voucher);
        //then
        assertThat(saveVoucher.getVoucherId(), is(voucherId));
    }

    @Test
    @DisplayName("customerId를 포함한 Voucher를 저장할 수 있어야 한다.")
    void insertVoucherTest() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testCustomer", "test@Email.com", CustomerType.BASIC, LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(), 1000L, VoucherType.FIXED, LocalDateTime.now());
        //when
        Voucher saveVoucher = voucherRepository.save(voucher);
        //then
        assertThat(saveVoucher.getCustomerId(), is(customer.getCustomerId()));
    }

    @Test
    @DisplayName("동일한 Id를 가진 voucher는 저장할 수 없다")
    void insertDuplicateVoucherTest() {
        //given
        UUID sameVoucherId = UUID.randomUUID();
        Voucher voucherOne = new FixedAmountVoucher(sameVoucherId, 1000L, VoucherType.FIXED, LocalDateTime.now());
        Voucher voucherTwo = new FixedAmountVoucher(sameVoucherId, 1000L, VoucherType.FIXED, LocalDateTime.now());
        //when
        voucherRepository.save(voucherOne);
        //then
        assertThrows(RuntimeException.class, () -> voucherRepository.save(voucherTwo));
    }

    @Test
    @DisplayName("Voucher의 amount를 수정할 수 있어야한다.")
    void updateVoucherAmountTest() {
        //given
        Long changAmount = 2000L;
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, VoucherType.FIXED, LocalDateTime.now());
        Voucher saveVoucher = voucherRepository.save(voucher);
        //when
        voucher.changeAmount(changAmount);
        Voucher updateVoucher = voucherRepository.update(voucher);
        //then
        assertThat(updateVoucher.getVoucherId(), is(saveVoucher.getVoucherId()));
        assertThat(updateVoucher.getAmount(), not(saveVoucher.getAmount()));
        assertThat(updateVoucher.getAmount(), is(changAmount));
    }

    @Test
    @DisplayName("Voucher의 customerId를 수정할 수 있어야한다.")
    void updateVoucherCustomerIdTest() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test", "test@email.com", CustomerType.BASIC, LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, VoucherType.FIXED, LocalDateTime.now());
        Voucher saveVoucher = voucherRepository.save(voucher);
        //when
        voucher.assignVoucherToCustomer(customer);
        Voucher updateVoucher = voucherRepository.update(voucher);
        //then
        assertThat(updateVoucher.getVoucherId(), is(saveVoucher.getVoucherId()));
        assertThat(updateVoucher.getCustomerId(), not(saveVoucher.getCustomerId()));
        assertThat(updateVoucher.getCustomerId(), is(customerId));
    }

    @Test
    @DisplayName("새로운 Voucher는 업데이트가 불가능하다")
    void notSavedVoucherUpdate(){
        //given
        Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, VoucherType.FIXED, LocalDateTime.now());
        //when,then
        assertThrows(RuntimeException.class, () -> voucherRepository.update(newVoucher));
    }

    @Test
    @DisplayName("VoucherId로 데이터를 조회할 수 있다.")
    void findByIdTest(){
        //given
        UUID voucherId = UUID.randomUUID();
        voucherRepository.save(new FixedAmountVoucher(voucherId, 1000L, VoucherType.FIXED, LocalDateTime.now()));
        //when
        Optional<Voucher> findVoucher = voucherRepository.findById(voucherId);
        //then
        assertThat(findVoucher.isEmpty(), is(false));
    }

    @Test
    @DisplayName("저장되지 않은 voucherId로는 조회 경우 빈 Optional을 반환한다..")
    void findByNotStoredIdTest(){
        //given
        UUID notSavedVoucherId = UUID.randomUUID();
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 1000L, VoucherType.FIXED, LocalDateTime.now()));
        //when
        Optional<Voucher> findVoucherByNotSavedId = voucherRepository.findById(notSavedVoucherId);
        //then
        assertThat(findVoucherByNotSavedId.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객 Id로 할당된 여러 voucher를 조회할 수 있다.")
    void findByCustomerIdTest() {
        //given
        UUID customerId = UUID.randomUUID();
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), customerId,1000L, VoucherType.FIXED, LocalDateTime.now()));
        //when
        List<Voucher> vouchers = voucherRepository.findByCustomerId(customerId);
        //then
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("할당되지 않은 고객 Id로 조회할 경우 빈 리스트를 반환한다.")
    void findByNoAssignedCustomerIdTest(){
        //given
        UUID customerId = UUID.randomUUID();
        //when
        List<Voucher> vouchers = voucherRepository.findByCustomerId(customerId);
        //then
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("전체를 조회할 수 있다.")
    void findAllTest(){
        //given
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 10L, VoucherType.PERCENT, LocalDateTime.now()));
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 20L, VoucherType.PERCENT, LocalDateTime.now()));
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 30L, VoucherType.PERCENT, LocalDateTime.now()));
        //when
        List<Voucher> vouchers = voucherRepository.findAll();
        //then
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.size(), is(3));
    }

    @Test
    @DisplayName("전체를 삭제할 수 있다.")
    void deleteAllTest(){
        //given
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 10L, VoucherType.PERCENT, LocalDateTime.now()));
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 20L, VoucherType.PERCENT, LocalDateTime.now()));
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 30L, VoucherType.PERCENT, LocalDateTime.now()));
        //when
        voucherRepository.deleteAll();
        //then
        assertThat(voucherRepository.findAll().isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객Id로 해당 voucher들을 삭제할 수 있다.")
    void deleteByCustomerId(){
        //given
        UUID customerIdOne = UUID.randomUUID();
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), customerIdOne,10L, VoucherType.PERCENT, LocalDateTime.now()));
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 20L, VoucherType.PERCENT, LocalDateTime.now()));
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 30L, VoucherType.PERCENT, LocalDateTime.now()));
        //when
        voucherRepository.deleteByCustomerId(customerIdOne);
        //then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.size(),is(2));
    }
}