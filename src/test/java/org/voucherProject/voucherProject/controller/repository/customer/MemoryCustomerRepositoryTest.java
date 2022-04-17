package org.voucherProject.voucherProject.controller.repository.customer;

import org.junit.jupiter.api.BeforeEach;
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
class MemoryCustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherRepository voucherRepository;

    UUID id;

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();

        Customer customer1 = new Customer(UUID.randomUUID(), "aaa", "aaa@.com", "1234");
        customerRepository.save(customer1);
        id = customer1.getCustomerId();
    }

    @Test
    void findById() {
        Optional<Customer> findById = customerRepository.findById(id);
        assertThat(findById.isPresent()).isTrue();
    }

    @Test
    void findByName() {
        Optional<Customer> byName = customerRepository.findByName("aaa");
        assertThat(byName.isPresent()).isTrue();
    }

    @Test
    void findByEmail() {
        Optional<Customer> byEmail = customerRepository.findByEmail("aaa@.com");
        assertThat(byEmail.isPresent()).isTrue();
    }

    @Test
    void findAll() {
        List<Customer> all = customerRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void findByVoucherType() throws Exception {
        Customer customer2 = new Customer(UUID.randomUUID(), "bbb", "bbb@.com", "1234");
        Customer customer3 = new Customer(UUID.randomUUID(), "ccc", "ccc@.com", "1234");

        customerRepository.save(customer2);
        customerRepository.save(customer3);

        Voucher voucher1 = VoucherType.FIXED.createVoucher(10, id);
        Voucher voucher2 = VoucherType.PERCENT.createVoucher(11, id);
        Voucher voucher3 = VoucherType.FIXED.createVoucher(12, customer2.getCustomerId());
        Voucher voucher4 = VoucherType.PERCENT.createVoucher(13, customer2.getCustomerId());
        Voucher voucher5 = VoucherType.FIXED.createVoucher(12, customer3.getCustomerId());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);
        voucherRepository.save(voucher5);

        List<Customer> byVoucherType1 = customerRepository.findByVoucherType(VoucherType.FIXED);
        List<Customer> byVoucherType2 = customerRepository.findByVoucherType(VoucherType.PERCENT);

        assertThat(byVoucherType1.size()).isEqualTo(3);
        assertThat(byVoucherType2.size()).isEqualTo(2);
    }

    @Test
    public void update() throws Exception {
        Customer customer2 = new Customer(UUID.randomUUID(), "bbb", "bbb@.com", "1234");
        Customer customer3 = new Customer(UUID.randomUUID(), "ccc", "ccc@.com", "1234");

        customerRepository.save(customer2);
        customerRepository.save(customer3);

        customer2.updatePassword("4321");
        customerRepository.update(customer2);

        Optional<Customer> byId = customerRepository.findById(customer2.getCustomerId());

        assertThat(byId.get().getPassword()).isEqualTo("4321");

    }
}