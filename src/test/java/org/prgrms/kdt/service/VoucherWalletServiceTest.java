package org.prgrms.kdt.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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

        Optional<Map<UUID, Voucher>> voucherList = voucherWalletService.getVoucherListByCustomerEmail(customer.getEmail());

        assertThat(voucherList.isPresent(), is(true));
        assertThat(voucherList.get().get(voucher.getVoucherId()).getVoucherId(), equalTo(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("사용자 이메일이 없거나, 보유하고 있는 바우처가 없는경우 바우처리스트를 가져올 수 없다")
    void getVoucherListByCustomerEmailValidate() {
        Customer customer = new Customer(UUID.randomUUID(),"test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        jdbcVoucherRepository.insertVoucher(voucher);

        Optional<Map<UUID, Voucher>> unknownCustomerVoucherList = voucherWalletService.getVoucherListByCustomerEmail("unknown@gmail.com");
        Optional<Map<UUID, Voucher>> newCustomerVoucherList = voucherWalletService.getVoucherListByCustomerEmail(customer.getEmail());
        assertThat(unknownCustomerVoucherList.isEmpty(), is(true));
        assertThat(newCustomerVoucherList.isEmpty(), is(true));
    }


}