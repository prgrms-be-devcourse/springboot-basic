package org.voucherProject.voucherProject.repository.voucher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.repository.CustomerRepository;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import org.voucherProject.voucherProject.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    Voucher saveVoucher1;
    Voucher saveVoucher2;
    Customer saveCustomer;
    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "a@naver.com", "1234");
        saveCustomer = customerRepository.save(customer);
        Voucher voucher = VoucherType.FIXED.createVoucher(10, customer.getCustomerId());
        Voucher voucher2 = VoucherType.FIXED.createVoucher(20, customer.getCustomerId());
        saveVoucher1 = voucherRepository.save(voucher);
        saveVoucher2 = voucherRepository.save(voucher2);
    }

    @Test
    @DisplayName("고객이 보유한 바우처를 조회")
    public void findByCustomerId() throws Exception {
        List<Voucher> vouchersByCustomer = voucherRepository.findByCustomerId(saveCustomer.getCustomerId());
        assertThat(vouchersByCustomer.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("고객이 보유한 바우처를 1개 제거")
    public void deleteOneVoucherByCustomerId() throws Exception {
        voucherRepository.deleteOneByCustomerId(saveCustomer.getCustomerId(),saveVoucher1.getVoucherId());

        Optional<Voucher> byId = voucherRepository.findById(saveVoucher1.getVoucherId());
        assertThat(byId.isEmpty()).isTrue();

    }

}