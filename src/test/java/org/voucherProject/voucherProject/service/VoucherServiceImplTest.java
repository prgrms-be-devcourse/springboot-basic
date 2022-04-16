package org.voucherProject.voucherProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.repository.CustomerRepository;
import org.voucherProject.voucherProject.customer.service.CustomerService;
import org.voucherProject.voucherProject.voucher.entity.FixedAmountVoucher;
import org.voucherProject.voucherProject.voucher.entity.PercentDiscountVoucher;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import org.voucherProject.voucherProject.voucher.repository.VoucherRepository;
import org.voucherProject.voucherProject.voucher.service.VoucherService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherServiceImplTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void clear() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Autowired
    VoucherService voucherService;

    @Autowired
    CustomerService customerService;

    @Test
    public void saveAndGet() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1, UUID.randomUUID());
        Voucher saveVoucher = voucherService.save(voucher);

        Voucher getVoucher = voucherService.getVoucher(saveVoucher.getVoucherId());
        assertThat(saveVoucher.getVoucherId()).isEqualTo(getVoucher.getVoucherId());
    }

    @Test
    public void saveSame() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1, UUID.randomUUID());
        voucherService.save(voucher);

        assertThrows(RuntimeException.class, () -> voucherService.save(voucher));
    }

    @Test
    public void findNull() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> voucherService.getVoucher(UUID.randomUUID()));
    }

    @Test
    public void findAll() throws Exception {
        Voucher saveVoucher1 = voucherService.save(new FixedAmountVoucher(UUID.randomUUID(), 11, UUID.randomUUID()));
        Voucher saveVoucher2 = voucherService.save(new FixedAmountVoucher(UUID.randomUUID(), 12, UUID.randomUUID()));
        Voucher saveVoucher3 = voucherService.save(new PercentDiscountVoucher(UUID.randomUUID(), 13, UUID.randomUUID()));
        Voucher saveVoucher4 = voucherService.save(new PercentDiscountVoucher(UUID.randomUUID(), 14, UUID.randomUUID()));

        List<Voucher> findAllVoucher = voucherService.findAll();
        assertThat(findAllVoucher.size()).isEqualTo(4);
    }

    @Test
    public void findByCustomer() throws Exception {
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "a@naver.com", "1234");
        Customer saveCustomer = customerService.save(customer);
        Voucher voucher = VoucherType.FIXED.createVoucher(10, customer.getCustomerId());
        Voucher voucher2 = VoucherType.FIXED.createVoucher(20, customer.getCustomerId());
        Voucher saveVoucher1 = voucherService.save(voucher);
        Voucher saveVoucher2 = voucherService.save(voucher2);

        List<Voucher> byCustomer = voucherService.findByCustomer(saveCustomer);
        assertThat(byCustomer.size()).isEqualTo(2);
    }
}