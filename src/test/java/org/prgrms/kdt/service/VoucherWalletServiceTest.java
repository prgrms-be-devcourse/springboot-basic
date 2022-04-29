package org.prgrms.kdt.service;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.TestConfiguration;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherWalletServiceTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    VoucherWalletService voucherWalletService;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    CustomerJdbcRepository customerRepository;

    @BeforeAll
    void clean() {
        TestConfiguration.clean(embeddedMysql);
    }

    @AfterEach
    void cleanTable() {
        jdbcVoucherRepository.deleteAllVouchers();
        customerRepository.deleteAllCustomer();
    }

    @Test
    @DisplayName("사용자이메일로 사용자가 보유한 바우처를 조회할 수 있다")
    void getVoucherListByCustomerEmail() {
        Customer customer = new Customer(UUID.randomUUID(),"test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        jdbcVoucherRepository.insertVoucher(voucher);
        jdbcVoucherRepository.updateVoucherOwner(voucher.getVoucherId(), customer.getCustomerId());

        VoucherMap voucherMap = voucherWalletService.getVoucherListByCustomerEmail(customer.getEmail());

        assertThat(voucherMap.isEmptyMap(), is(false));
    }

    @Test
    @DisplayName("사용자 이메일이 없거나, 보유하고 있는 바우처가 없는경우 바우처리스트를 가져올 수 없다")
    void getVoucherListByCustomerEmailValidate() {
        Customer customer = new Customer(UUID.randomUUID(),"test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        jdbcVoucherRepository.insertVoucher(voucher);

        VoucherMap unknownCustomerVoucherMap = voucherWalletService.getVoucherListByCustomerEmail("unknown@gmail.com");
        VoucherMap newCustomerVoucherList = voucherWalletService.getVoucherListByCustomerEmail(customer.getEmail());
        assertThat(unknownCustomerVoucherMap.isEmptyMap(), is(true));
        assertThat(newCustomerVoucherList.isEmptyMap(), is(true));
    }
    @Test
    void selectJoinVoucherCustomerByVoucherId() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());

        jdbcVoucherRepository.insertVoucher(fixedAmountVoucher);
        Optional<Voucher> voucher = voucherWalletService.getVoucherWalletById(fixedAmountVoucher.getVoucherId());
        assertThat(voucher.isPresent(), is(true));
    }
    
}