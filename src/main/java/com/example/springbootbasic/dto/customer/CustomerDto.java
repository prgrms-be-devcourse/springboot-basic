package com.example.springbootbasic.dto.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.dto.voucher.VoucherDto;

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

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public List<VoucherDto> getVouchers() {
        return vouchers;
    }
}
