package org.prgrms.kdt.repository;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.TestConfiguration;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    CustomerJdbcRepository customerRepository;

    FixedAmountVoucher fixedAmountVoucher;

    @BeforeAll
    void setup() {
        TestConfiguration.clean(embeddedMysql);
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 30, LocalDateTime.now());
    }

    @AfterEach
    void clean() {
        jdbcVoucherRepository.deleteAllVouchers();
    }

    @Test
    @DisplayName("바우처를 넣을 수 있다.")
    void insert() {
        var insertVoucher = jdbcVoucherRepository.insertVoucher(fixedAmountVoucher);
        var receiveVoucher = jdbcVoucherRepository.getByVoucherId(insertVoucher.getVoucherId());
        assertThat(insertVoucher.getVoucherId(), equalTo(receiveVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처를 바우처Id로 가져 올 수 있다.")
    void getVoucher() {
        jdbcVoucherRepository.insertVoucher(fixedAmountVoucher);
        Voucher voucher = jdbcVoucherRepository.getByVoucherId(fixedAmountVoucher.getVoucherId());
        assertThat(voucher.getVoucherId(), equalTo(fixedAmountVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("없는 바우처를 가져오려고하면 에러가 발생한다.")
    void getEmptyVoucher() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 40, LocalDateTime.now());
        assertThrows(EmptyResultDataAccessException.class,
                () -> jdbcVoucherRepository.getByVoucherId(voucher.getVoucherId()));

    }

    @Test
    @DisplayName("전체 바우처 리스트를 가져올 수 있다.")
    void getVoucherList() {
        jdbcVoucherRepository.insertVoucher(fixedAmountVoucher);
        var voucherList = jdbcVoucherRepository.getVoucherList();
        assertThat(voucherList.isEmpty(), is(false));
        assertThat(voucherList.size(), equalTo(1));
    }

    @Test
    @DisplayName("바우처를 삭제 할 수 있다.")
    void delete() {
        jdbcVoucherRepository.insertVoucher(fixedAmountVoucher);
        jdbcVoucherRepository.deleteVoucherById(fixedAmountVoucher.getVoucherId());
        var voucherList = jdbcVoucherRepository.getVoucherList();
        assertThat(voucherList.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 owner 값을 update 할 수 있다.")
    void updateVoucherOwner() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        jdbcVoucherRepository.insertVoucher(fixedAmountVoucher);
        var voucher = jdbcVoucherRepository.updateVoucherOwner(fixedAmountVoucher.getVoucherId(), customer.getCustomerId());
        assertThat(customer.getCustomerId(), is(voucher.getCustomer().getCustomerId()));
    }

    @Test
    @DisplayName("customers 테이블에 등록되지 않은 사용자에게 바우처 할당시 foreingkey 에러가난다.")
    void updateForeignKeyConstrains() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com",
                LocalDateTime.now(), LocalDateTime.now());
        jdbcVoucherRepository.insertVoucher(fixedAmountVoucher);
        assertThrows(DataIntegrityViolationException.class,
                () -> jdbcVoucherRepository
                        .updateVoucherOwner(fixedAmountVoucher.getVoucherId(), customer.getCustomerId()));

    }
}