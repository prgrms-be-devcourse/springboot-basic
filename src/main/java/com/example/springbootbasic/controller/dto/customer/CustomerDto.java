package com.example.springbootbasic.controller.dto.customer;

import com.example.springbootbasic.controller.dto.voucher.VoucherDto;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerDto {

    private final Long customerId;
    private final CustomerStatus status;
    private final List<VoucherDto> vouchers;

    private CustomerDto(Long customerId, CustomerStatus status, List<VoucherDto> vouchers) {
        this.customerId = customerId;
        this.status = status;
        this.vouchers = vouchers;
    }

    public static CustomerDto newInstance(Customer customer) {
        List<VoucherDto> voucherDtos = customer.getVouchers()
                .stream()
                .map(VoucherDto::newInstance)
                .collect(Collectors.toList());
        return new CustomerDto(customer.getCustomerId(), customer.getStatus(), voucherDtos);
    }

    public List<VoucherDto> getVouchers() {
        return vouchers;
    }

    public boolean isEmpty() {
        return  customerId == null || customerId == 0 || status == null;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}
