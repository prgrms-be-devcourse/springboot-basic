package org.prgrms.kdt.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcWalletRepositoryTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    JdbcWalletRepository jdbcWalletRepository;

    @Autowired
    CustomerJdbcRepository customerRepository;

    FixedAmountVoucher fixedAmountVoucher;

    PercentDiscountVoucher percentDiscountVoucher;

    @BeforeAll
    void clean() {
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 30, LocalDateTime.now());
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30, LocalDateTime.now());
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

    @Test
    @DisplayName("지갑에서는 사용자에게 할당되지 않은 바우처이면, 바우처값을 가져올 수 없다.")
    void getVoucherUnprovided() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> jdbcWalletRepository.selectJoinVoucherCustomer(fixedAmountVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("사용자에게 할당된 바우처면, 바우처값을 가져올 수 있다.")
    void getVoucherProvided() {
        Customer newCustomer = new Customer(UUID.randomUUID(),"test","test@gmail.com",LocalDateTime.now() ,LocalDateTime.now());
        Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        customerRepository.insertCustomer(newCustomer);
        jdbcVoucherRepository.insertVoucher(newVoucher);
        jdbcVoucherRepository.updateVoucherOwner(newVoucher.getVoucherId(), newCustomer.getCustomerId());
        Voucher voucher = jdbcWalletRepository.selectJoinVoucherCustomer(newVoucher.getVoucherId());
        assertThat(voucher.getCustomer().getCustomerId(), equalTo(newCustomer.getCustomerId()));
    }
}