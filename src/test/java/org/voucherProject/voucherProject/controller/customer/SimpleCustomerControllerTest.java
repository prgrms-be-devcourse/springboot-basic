package org.voucherProject.voucherProject.controller.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.controller.CustomerController;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.entity.CustomerDto;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import org.voucherProject.voucherProject.customer.service.CustomerService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SimpleCustomerControllerTest {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerService customerService;

    Customer customer;
    CustomerDto customerDto;
    @BeforeEach
    void setUp() {
        customerService.deleteAll();
        Customer aaa = new Customer(UUID.randomUUID(), "aaa", "a@.com");
        Voucher voucher = VoucherType.FIXED.createVoucher(10, aaa.getCustomerId());
        UUID voucherId = voucher.getVoucherId();
        customerDto = CustomerDto.builder()
                .customerId(UUID.randomUUID())
                .customerName("aaa")
                .customerEmail("aaa@.com")
                .password("1234")
                .build();
        customer = customerController.createCustomer(customerDto);
    }

    @Test
    void findAll() {
        List<CustomerDto> all = customerController.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        CustomerDto byId = customerController.findById(customerDto);
        assertThat(byId.getCustomerId()).isEqualTo(customerDto.getCustomerId());
        assertThat(byId.getCustomerName()).isEqualTo(customerDto.getCustomerName());
        assertThat(byId.getCustomerEmail()).isEqualTo(customerDto.getCustomerEmail());
    }

    @Test
    void findByName() {
        CustomerDto byName = customerController.findByName(customerDto);
        assertThat(byName.getCustomerId()).isEqualTo(customerDto.getCustomerId());
        assertThat(byName.getCustomerName()).isEqualTo(customerDto.getCustomerName());
        assertThat(byName.getCustomerEmail()).isEqualTo(customerDto.getCustomerEmail());
    }

    @Test
    void findByEmail() {
        CustomerDto byEmail = customerController.findByEmail(customerDto);
        assertThat(byEmail.getCustomerId()).isEqualTo(customerDto.getCustomerId());
        assertThat(byEmail.getCustomerName()).isEqualTo(customerDto.getCustomerName());
        assertThat(byEmail.getCustomerEmail()).isEqualTo(customerDto.getCustomerEmail());
    }
}