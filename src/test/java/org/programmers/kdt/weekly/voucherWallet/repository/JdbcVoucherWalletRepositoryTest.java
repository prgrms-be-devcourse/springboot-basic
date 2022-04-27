package org.programmers.kdt.weekly.voucherWallet.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.kdt.weekly.customer.model.Customer;
import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.programmers.kdt.weekly.customer.repository.CustomerRepository;
import org.programmers.kdt.weekly.voucher.model.FixedAmountVoucher;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.programmers.kdt.weekly.voucherWallet.model.VoucherWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherWalletRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(v5_7_latest)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-voucher_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    VoucherWalletRepository voucherWalletRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VoucherRepository voucherRepository;

    Customer customer = new Customer(UUID.randomUUID(), "hyeb@gmail.com", "hyeb",
        CustomerType.NORMAL);
    FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 123,
        LocalDateTime.now());
    VoucherWallet voucherWallet = new VoucherWallet(UUID.randomUUID(), customer.getCustomerId(),
        voucher.getVoucherId(), LocalDateTime.now());

    @BeforeAll
    void setUp() {
        customerRepository.insert(customer);
        voucherRepository.insert(voucher);
    }

    @BeforeEach
    void set() {
        voucherWalletRepository.deleteAllByCustomerId(customer.getCustomerId());
        voucherWalletRepository.insert(voucherWallet);
    }

    @Test
    @DisplayName("voucherWalletId가 중복되어서 DuplicateKeyException 발생")
    void insert() {
        //given
        //when
        //then
        assertThatThrownBy(() -> voucherWalletRepository.insert(voucherWallet)).isInstanceOf(
            DuplicateKeyException.class);
    }

    @Test
    @DisplayName("voucher Wallet list가 return 되어야함")
    void findAll() {
        //given
        //when
        var all = voucherWalletRepository.findAll();
        //then
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @DisplayName("customerId에 해당하는 voucher Wallet이 return 되어야함")
    void findByCustomerId() {
        //given
        //when
        var voucherWallets = voucherWalletRepository.findByCustomerId(customer.getCustomerId());
        //then
        assertThat(voucherWallets.isEmpty(), is(false));
    }

    @Test
    @DisplayName("walletId에 해당하는 voucher Wallet이 return 되어야함")
    void findByWalletId() {
        //given
        //when
        var findVoucherWallet = voucherWalletRepository.findByCustomerId(
            voucherWallet.getWalletId());
        //then
        assertThat(voucherWallet, samePropertyValuesAs(findVoucherWallet));
    }

    @Test
    @DisplayName("customerId와 voucherWalletId에 해당하는 wallet이 삭제 되어야함")
    void deleteById() {
        //given
        //when
        var uuid = voucherWalletRepository.deleteById(customer.getCustomerId(),
            voucherWallet.getWalletId());
        //then
        assertThat(uuid.isPresent(), is(true));
        assertThat(voucherWallet.getWalletId(), samePropertyValuesAs(uuid));
    }

    @Test
    @DisplayName("wallet이 모두 삭제 되어야함")
    void deleteAllByCustomerId() {
        //given
        //when
        voucherWalletRepository.deleteAllByCustomerId(customer.getCustomerId());
        //then
        var findWallet = voucherWalletRepository.findByCustomerId(customer.getCustomerId());
        assertThat(findWallet.isEmpty(), is(true));
    }
}