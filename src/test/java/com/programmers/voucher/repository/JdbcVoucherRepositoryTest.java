package com.programmers.voucher.repository;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.repository.CustomerRepository;
import com.programmers.voucher.domain.TypeOfVoucher;
import com.programmers.voucher.domain.Voucher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    @Qualifier("Jdbc")
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    UUID newId = UUID.randomUUID();
    @BeforeAll
    void setup() {
        Customer customer1 = new Customer("super1@gmail.com", "1234", "Sul", LocalDateTime.now());
        Customer customer2 = new Customer("dark1@gmail.com", "5678", "Jung", LocalDateTime.now());
        customerRepository.insert(customer1);
        customerRepository.insert(customer2);

    }

    @BeforeEach
    void setVouchers() {
        Voucher voucher1 = new Voucher(UUID.randomUUID(), TypeOfVoucher.FIXED_AMOUNT_VOUCHER, 2000, LocalDateTime.now(), LocalDateTime.now().plusMonths(6), "super1@gmail.com");
        Voucher voucher2 = new Voucher(newId, TypeOfVoucher.PERCENT_DISCOUNT_VOUCHER, 20, LocalDateTime.now(), LocalDateTime.now().plusMonths(3), "super1@gmail.com");
        Voucher voucher3 = new Voucher(UUID.randomUUID(), TypeOfVoucher.PERCENT_DISCOUNT_VOUCHER, 50, LocalDateTime.now(), LocalDateTime.now().plusMonths(3), "dark1@gmail.com");
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);
    }

    @AfterEach
    void deleteVouchers() {
        voucherRepository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    void insert() {
        Voucher voucher = new Voucher(UUID.randomUUID(), TypeOfVoucher.FIXED_AMOUNT_VOUCHER, 1000, LocalDateTime.now(), LocalDateTime.now().plusMonths(6),"super1@gmail.com");
        voucherRepository.insert(voucher);

        var retrievedVoucher = voucherRepository.findByVoucherId(voucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    void update() {
        long newDiscount = 30L;
        voucherRepository.update(newId, newDiscount);

        var retrievedVoucher = voucherRepository.findByVoucherId(newId);

        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get().getDiscount(), is(newDiscount));
    }

    @Test
    void findAll() {
        var vouchers = voucherRepository.findAll();
        assertThat(vouchers.size(), is(3));
    }

    @Test
    void findByCustomerEmail() {
        var vouchers1 = voucherRepository.findByCustomerEmail("super1@gmail.com");
        var vouchers2 = voucherRepository.findByCustomerEmail("dark1@gmail.com");

        assertThat(vouchers1.size(), is(2));
        assertThat(vouchers2.size(), is(1));
    }


    @Test
    void findByVoucherId() {
        var voucher = voucherRepository.findByVoucherId(newId);
        assertThat(voucher.isEmpty(), is(false));
    }

    @Test
    void deleteByVoucherId() {
        voucherRepository.deleteByVoucherId(newId);
        var vouchers = voucherRepository.findAll();
        assertThat(vouchers.size(), is(2));
    }

    @Test
    void deleteAll() {
        voucherRepository.deleteAll();
        var vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }
}