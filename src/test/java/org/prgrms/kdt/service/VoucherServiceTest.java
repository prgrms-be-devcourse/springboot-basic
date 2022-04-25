package org.prgrms.kdt.service;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.TestConfiguration;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.prgrms.kdt.repository.JdbcWalletRepository;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.repository.voucher.MemoryVoucherRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    CustomerJdbcRepository customerRepository;

    @Autowired
    VoucherService voucherService;

    @Autowired
    JdbcWalletRepository jdbcWalletRepository;

    @BeforeAll
    void clean() {
        TestConfiguration.clean(embeddedMysql);
    }

    @AfterEach
    void cleanTable() {
        jdbcVoucherRepository.deleteAllVouchers();
        customerRepository.deleteAllCustomer();
    }

    @DisplayName("유효한 voucherType으로만 바우처를 생성할 수 있다.")
    @Test
    void createVoucherByValidateVoucherType() {
        assertAll(
                () -> assertThrows(Exception.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository, jdbcWalletRepository).createVoucher(UUID.randomUUID(), 3, 100)),
                () -> assertThat(new VoucherService(new MemoryVoucherRepository(), customerRepository, jdbcWalletRepository).createVoucher(UUID.randomUUID(), 1, 100).getClass(), is(FixedAmountVoucher.class)),
                () -> assertThat(new VoucherService(new MemoryVoucherRepository(), customerRepository, jdbcWalletRepository).createVoucher(UUID.randomUUID(), 2, 100).getClass(), is(PercentDiscountVoucher.class))
        );
    }

    @DisplayName("유효한 discountAmount로만 생성 할 수 있다.")
    @Test
    void createVoucherByValidateDiscountAmount() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository, jdbcWalletRepository).createVoucher(UUID.randomUUID(), 1, -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(),customerRepository, jdbcWalletRepository).createVoucher(UUID.randomUUID(), 1, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository, jdbcWalletRepository).createVoucher(UUID.randomUUID(), 2, -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository, jdbcWalletRepository).createVoucher(UUID.randomUUID(), 2, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository(), customerRepository, jdbcWalletRepository).createVoucher(UUID.randomUUID(), 2, 110))
        );
    }


    @DisplayName("바우처가 생성되어야 한다.")
    @Test
    void createVoucher() {
        VoucherService voucherService = new VoucherService(new MemoryVoucherRepository(), customerRepository, jdbcWalletRepository);

        Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), 1, 100);

        assertThat(voucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(voucher.getDiscountAmount(), is(100L));
        assertThat(voucher, notNullValue());
    }

    @DisplayName("바우처가 생성되어야 한다. (mock) - voucher : null")
    @Test
    @Disabled
    void createVoucherByMock() {
        VoucherService voucherService = mock(VoucherService.class);

        Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), 1, 100);

        assertThat(voucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(voucher.getDiscountAmount(), is(100L));
        assertThat(voucher, notNullValue());
    }

    @DisplayName("바우처 리스트가 출력되어야 한다.")
    @Test
    void printVoucherList() {
        VoucherRepository voucherRepository = mock(MemoryVoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository, customerRepository, jdbcWalletRepository);

        voucherService.createVoucher(UUID.randomUUID(), 1, 100);
        voucherService.getVoucherList();

        verify(voucherRepository).getVoucherList();
    }

    @DisplayName("등록된 바우처가 없으면 바우처리스트가 출력되지 않는다.")
    @Test
    void printEmptyVoucherList() {
        VoucherRepository voucherRepository = mock(MemoryVoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository, customerRepository, jdbcWalletRepository);

        voucherService.getVoucherList();

        assertThat((Map<UUID, Voucher>) voucherRepository.getVoucherList(), anEmptyMap());
    }

    @DisplayName("바우처를 고객에게 할당 할 수 있다.")
    @Test
    void provideVoucherToCustomer() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        jdbcVoucherRepository.insertVoucher(voucher);

        Optional<Voucher> returnVoucher = voucherService.provideVoucherToCustomer(voucher.getVoucherId().toString(), customer.getCustomerId().toString());

        assertThat(returnVoucher.get().getVoucherId(), equalTo(voucher.getVoucherId()));
    }

    @DisplayName("voucherId가 유효하지 않으면 고객에게 바우처를 할당 할 수 없다.")
    @Test
    void validateVoucherIdWhenProvide() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        Optional<Voucher> returnVoucher = voucherService.provideVoucherToCustomer(UUID.randomUUID().toString(), customer.getCustomerId().toString());

        assertThat(returnVoucher.isEmpty(), is(true));
    }

    @DisplayName("customerId가 유효하지 않으면 고객에게 바우처를 할당 할 수 없다.")
    @Test
    void validateCustomerIdWhenProvide() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        jdbcVoucherRepository.insertVoucher(voucher);

        Optional<Voucher> returnVoucher = voucherService.provideVoucherToCustomer(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        assertThat(returnVoucher.isEmpty(), is(true));
    }

    @DisplayName("이미 할당되어있는 바우처는 고객에게 재할당 할 수 없다.")
    @Test
    void validateReprovide() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        jdbcVoucherRepository.insertVoucher(voucher);
        jdbcVoucherRepository.updateVoucherOwner(voucher.getVoucherId(), customer.getCustomerId());

        Optional<Voucher> returnVoucher = voucherService.provideVoucherToCustomer(voucher.getVoucherId().toString(), customer.getCustomerId().toString());
        assertThat(returnVoucher.isEmpty(), is(true));
    }

    @DisplayName("고객에게 할당된 바우처를 삭제한다.")
    @Test
    void deleteVoucher() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300, LocalDateTime.now());
        customerRepository.insertCustomer(customer);
        jdbcVoucherRepository.insertVoucher(voucher);
        jdbcVoucherRepository.updateVoucherOwner(voucher.getVoucherId(), customer.getCustomerId());

        voucherService.deleteVoucher(voucher.getVoucherId(), customer.getEmail());

        assertThrows(EmptyResultDataAccessException.class, () -> jdbcVoucherRepository.getByVoucherId(voucher.getVoucherId()));
    }

}