package org.prgrms.kdt.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    CustomerRepository customerRepository;

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
    @Order(1)
    @DisplayName("바우처를 넣을 수 있다.")
    void insert() {
        var insertVoucher = jdbcVoucherRepository.insertVoucher(fixedAmountVoucher);
        var receiveVoucher = jdbcVoucherRepository.getByVoucherId(insertVoucher.getVoucherId());
        assertThat(insertVoucher.getVoucherId(), equalTo(receiveVoucher.getVoucherId()));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 바우처Id로 가져 올 수 있다.")
    void getVoucher() {
        Voucher voucher = jdbcVoucherRepository.getByVoucherId(fixedAmountVoucher.getVoucherId());
        assertThat(voucher.getVoucherId(), equalTo(fixedAmountVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("없는 바우처를 가져오려고하면 에러가 발생한다.")
    void getEmptyVoucher() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 40, LocalDateTime.now());
        assertThrows(EmptyResultDataAccessException.class, () -> jdbcVoucherRepository.getByVoucherId(voucher.getVoucherId()));

    }

    @Test
    @Order(2)
    @DisplayName("전체 바우처 리스트를 가져올 수 있다.")
    void getVoucherList() {
        var voucherList = jdbcVoucherRepository.getVoucherList();
        assertThat(voucherList.isEmpty(), is(false));
        assertThat(voucherList.size(), equalTo(1));
    }

    @Test
    @Order(3)
    @DisplayName("바우처를 삭제 할 수 있다.")
    void delete() {
        jdbcVoucherRepository.deleteVoucherById(fixedAmountVoucher.getVoucherId());
        var voucherList = jdbcVoucherRepository.getVoucherList();
        assertThat(voucherList.isEmpty(), is(true));
    }

    @Test
    @Order(2)
    @DisplayName("바우처 owner 값을 update 할 수 있다.")
    void updateVoucherOwner() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        var voucher = jdbcVoucherRepository.updateVoucherOwner(fixedAmountVoucher.getVoucherId(), customer.getCustomerId());
        assertThat(customer.getCustomerId(), is(voucher.getCustomer().getCustomerId()));
    }

    @Test
    @Order(2)
    @DisplayName("customers 테이블에 등록되지 않은 사용자에게 바우처 할당시 foreingkey 에러가난다.")
    void updateForeignKeyConstrains() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        assertThrows(DataIntegrityViolationException.class,
                () -> jdbcVoucherRepository.updateVoucherOwner(fixedAmountVoucher.getVoucherId(), customer.getCustomerId()));

    }
}