package org.voucherProject.voucherProject.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.repository.CustomerRepository;
import org.voucherProject.voucherProject.voucher.entity.FixedAmountVoucher;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherStatus;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import org.voucherProject.voucherProject.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    Voucher saveVoucher;
    Customer saveCustomer;
    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "a@naver.com", "1234");
        saveCustomer = customerRepository.save(customer);
        Voucher voucher = VoucherType.FIXED.createVoucher(10, customer.getCustomerId());
        saveVoucher = voucherRepository.save(voucher);
    }

    @Test
    public void save() throws Exception {

        List<Voucher> byCustomerId = voucherRepository.findByCustomerId(saveCustomer.getCustomerId());
        for (Voucher voucher : byCustomerId) {
            System.out.println("voucher = " + voucher);
        }


    }
}