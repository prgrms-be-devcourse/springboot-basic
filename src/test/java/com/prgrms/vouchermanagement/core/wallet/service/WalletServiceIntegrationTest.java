package com.prgrms.vouchermanagement.core.wallet.service;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;
import com.prgrms.vouchermanagement.core.customer.dto.CustomerDto;
import com.prgrms.vouchermanagement.core.customer.repository.CustomerRepository;
import com.prgrms.vouchermanagement.core.customer.repository.jdbc.JdbcCustomerRepository;
import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;
import com.prgrms.vouchermanagement.core.voucher.dto.VoucherDto;
import com.prgrms.vouchermanagement.core.voucher.repository.VoucherRepository;
import com.prgrms.vouchermanagement.core.voucher.repository.jdbc.JdbcVoucherRepository;
import com.prgrms.vouchermanagement.core.wallet.dto.WalletDto;
import com.prgrms.vouchermanagement.core.wallet.repository.WalletRepository;
import com.prgrms.vouchermanagement.core.wallet.repository.jdbc.JdbcWalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("prod")
@ContextConfiguration(classes = {WalletService.class, JdbcWalletRepository.class, JdbcCustomerRepository.class, JdbcVoucherRepository.class, WalletServiceIntegrationTest.Config.class})
class WalletServiceIntegrationTest {

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("sql/customer-init.sql")
                    .addScript("sql/voucher-init.sql")
                    .addScript("sql/wallet-init.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;


    /**
     * 기능 1: 특정 고객에게 특정 바우처를 할당
     */

    @DisplayName("특정 고객에게 특정 바우처를 할당할 수 있다.")
    @Test
    void testAssignVoucher() {
        // given
        String customerId = UUID.randomUUID().toString();
        Customer customer = new Customer(customerId, "test1", "test1@email.com");
        customerRepository.save(customer);

        String voucherId = UUID.randomUUID().toString();
        Voucher voucher = new Voucher(voucherId, "test2", 1000, VoucherType.FIXED);
        voucherRepository.save(voucher);

        // when
        WalletDto walletDto = walletService.assignVoucher(customerId, voucherId);

        // then
        assertAll(
                () -> assertThat(walletDto.getId()).isNotEmpty(),
                () -> assertThat(walletDto.getCustomerId()).isEqualTo(customerId),
                () -> assertThat(walletDto.getVoucherId()).isEqualTo(voucherId)
        );
    }

    @DisplayName("존재하지 않는 고객에게 특정 바우처를 할당할 수 없다.")
    @Test
    void testAssignVoucherWithNoExistCustomer() {
        // given
        String customerId = UUID.randomUUID().toString();

        String voucherId = UUID.randomUUID().toString();
        Voucher voucher = new Voucher(voucherId, "test3", 1000, VoucherType.FIXED);
        voucherRepository.save(voucher);

        // when

        // then
        assertThrows(IllegalArgumentException.class,
                () -> walletService.assignVoucher(customerId, voucherId));
    }

    @DisplayName("특정 고객에게 존재하지 않는 바우처를 할당할 수 없다.")
    @Test
    void testAssignVoucherWithNoExistVoucher() {
        // given
        String customerId = UUID.randomUUID().toString();
        Customer customer = new Customer(customerId, "test4", "test4@email.com");
        customerRepository.save(customer);

        String voucherId = UUID.randomUUID().toString();

        // when

        // then
        assertThrows(IllegalArgumentException.class,
                () -> walletService.assignVoucher(customerId, voucherId));
    }


    /**
     * 기능 2: 특정 고객이 보유한 바우처들 조회
     */
    @DisplayName("특정 고객이 보유한 바우처 목록을 조회할 수 있다.")
    @Test
    void testFindVouchersByCustomer() {
        // given
        String customerId = UUID.randomUUID().toString();
        Customer customer = new Customer(customerId, "test5", "test5@email.com");
        customerRepository.save(customer);

        String voucherId1 = UUID.randomUUID().toString();
        Voucher voucher1 = new Voucher(voucherId1, "test6", 1000, VoucherType.FIXED);
        voucherRepository.save(voucher1);

        String voucherId2 = UUID.randomUUID().toString();
        Voucher voucher2 = new Voucher(voucherId2, "test7", 1000, VoucherType.FIXED);
        voucherRepository.save(voucher2);

        String voucherId3 = UUID.randomUUID().toString();
        Voucher voucher3 = new Voucher(voucherId3, "test8", 1000, VoucherType.FIXED);
        voucherRepository.save(voucher3);

        walletService.assignVoucher(customerId, voucherId1);
        walletService.assignVoucher(customerId, voucherId2);

        // when
        List<VoucherDto> voucherDtoList = walletService.findVouchersByCustomer(customerId);
        List<String> idList = voucherDtoList.stream()
                .map(VoucherDto::getId)
                .collect(Collectors.toList());

        // then
        assertThat(idList.containsAll(List.of(voucherId1, voucherId2)));
    }

    @DisplayName("존재하지 않는 고객이 보유한 바우처 목록 조회 시 예외를 반환한다")
    @Test
    void testFindVouchersWithNoExistCustomer() {
        // given
        String customerId = UUID.randomUUID().toString();

        String voucherId = UUID.randomUUID().toString();
        Voucher voucher = new Voucher(voucherId, "test9", 1000, VoucherType.FIXED);
        voucherRepository.save(voucher);

        // when

        // then
        assertThrows(
                IllegalArgumentException.class,
                () -> walletService.findVouchersByCustomer(customerId)
        );
    }

    @DisplayName("바우처를 보유하지 않은 고객에 대한 바우처 목록 조회 시 빈 리스트를 반환한다.")
    @Test
    void testFindVouchersByCustomerWithNoVoucher() {
        // given
        String customerId = UUID.randomUUID().toString();
        Customer customer = new Customer(customerId, "test10", "test10@email.com");
        customerRepository.save(customer);

        // when
        List<VoucherDto> voucherDtoList = walletService.findVouchersByCustomer(customerId);

        // then
        assertThat(voucherDtoList.isEmpty()).isTrue();
    }


    /**
     * 기능 3: 특정 고객이 보유한 특정 바우처 삭제
     *
     */

    @DisplayName("특정 고객이 보유한 특정 바우처를 삭제할 수 있다.")
    @Test
    void testDeleteVouchersByCustomer() {
        // given

        // when

        // then
    }

    // 고객이 존재하지 않는 경우
    @DisplayName("존재하지 않는 고객에 대한 고객이 보유한 특정 바우처 삭제 시 예외를 반환한다.")
    @Test
    void testDeleteVouchersByNoExistCustomer() {
        // given

        // when

        // then
    }


    /**
     * 기능 4: 특정 바우처를 보유한 고객들 조회
     *
     */
    @DisplayName("특정 바우처를 보유한 고객들을 조회할 수 있다.")
    @Test
    void testFindCustomersByVoucher() {
        // given
        String voucherId = UUID.randomUUID().toString();
        Voucher voucher = new Voucher(voucherId, "test11", 1000, VoucherType.FIXED);
        voucherRepository.save(voucher);

        String customerId1 = UUID.randomUUID().toString();
        Customer customer1 = new Customer(customerId1, "test12", "test12@email.com");
        customerRepository.save(customer1);

        String customerId2 = UUID.randomUUID().toString();
        Customer customer2 = new Customer(customerId2, "test13", "test13@email.com");
        customerRepository.save(customer2);

        String customerId3 = UUID.randomUUID().toString();
        Customer customer3 = new Customer(customerId3, "test14", "test14@email.com");
        customerRepository.save(customer3);

        walletService.assignVoucher(customerId1, voucherId);
        walletService.assignVoucher(customerId2, voucherId);

        // when
        List<CustomerDto> customerDtoList = walletService.findCustomersByVoucher(voucherId);
        List<Customer> customerList = customerDtoList.stream()
                .map(it -> new Customer(it.getId(), it.getName(), it.getEmail()))
                .collect(Collectors.toList());

        // then
        assertThat(customerList).containsAll(List.of(customer1, customer2));
    }

    @DisplayName("특정 바우처를 보유한 고객들을 조회시 바우처가 존재하지 않으면 예외를 반환한다.")
    @Test
    void testFindCustomersByVoucherWithNoExistVoucher() {
        // given
        String voucherId = UUID.randomUUID().toString();

        // when

        // then
        assertThrows(IllegalArgumentException.class,
                () -> walletService.findCustomersByVoucher(voucherId));
    }

    @DisplayName("특정 바우처를 보유한 고객 조회 시 해당 바우처를 보유한 고객이 없으면 빈 리스트를 반환한다.")
    @Test
    void testFindCustomersByVoucherWithNoCustomer() {
        // given
        String voucherId = UUID.randomUUID().toString();
        Voucher voucher = new Voucher(voucherId, "test11", 1000, VoucherType.FIXED);
        voucherRepository.save(voucher);

        // when
        List<CustomerDto> customerDtoList = walletService.findCustomersByVoucher(voucherId);

        // then
        assertThat(customerDtoList.size()).isZero();
    }

}