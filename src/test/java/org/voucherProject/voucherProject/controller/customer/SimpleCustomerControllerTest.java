package org.voucherProject.voucherProject.controller.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.customer.Customer;
import org.voucherProject.voucherProject.entity.customer.CustomerDto;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.service.customer.CustomerService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        Voucher voucher = VoucherType.FIXED.createVoucher(10);
        UUID voucherId = voucher.getVoucherId();
        customerDto = CustomerDto.builder()
                .customerId(UUID.randomUUID())
                .customerName("aaa")
                .customerEmail("aaa@.com")
                .password("1234")
                .voucher(voucherId)
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
        assertThat(byId.getVouchers()).isEqualTo(customerDto.getVouchers());
    }

    @Test
    void findByName() {
        CustomerDto byName = customerController.findByName(customerDto);
        assertThat(byName.getCustomerId()).isEqualTo(customerDto.getCustomerId());
        assertThat(byName.getCustomerName()).isEqualTo(customerDto.getCustomerName());
        assertThat(byName.getCustomerEmail()).isEqualTo(customerDto.getCustomerEmail());
        assertThat(byName.getVouchers()).isEqualTo(customerDto.getVouchers());
    }

    @Test
    void findByEmail() {
        CustomerDto byEmail = customerController.findByEmail(customerDto);
        assertThat(byEmail.getCustomerId()).isEqualTo(customerDto.getCustomerId());
        assertThat(byEmail.getCustomerName()).isEqualTo(customerDto.getCustomerName());
        assertThat(byEmail.getCustomerEmail()).isEqualTo(customerDto.getCustomerEmail());
        assertThat(byEmail.getVouchers()).isEqualTo(customerDto.getVouchers());
    }
}