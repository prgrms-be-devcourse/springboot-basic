package com.programmers.customer.service;

import com.programmers.customer.Customer;
import com.programmers.customer.dto.CustomerDto;
import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.voucher.Voucher;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface CustomerService {
    CustomerDto join(String name, String email);

    CustomerDto findById(UUID customerId);

    CustomerDto findByName(String name);

    CustomerDto findByEmail(String email);

    CustomerDto findCustomerByVoucherId(UUID voucherId);

    CustomerDto update(Customer customer);

    List<CustomerDto> findAll();

    void deleteCustomer(UUID customerId);

    default CustomerDto entityToDto(Customer customer) {
        List<Voucher> wallet = customer.getWallet();
        List<VoucherDto> voucherDtoList = wallet.stream()
                .map(voucher -> new VoucherDto(
                        voucher.getVoucherId(),
                        voucher.getType(),
                        voucher.getValue(),
                        voucher.isAssigned()))
                .collect(Collectors.toList());

        CustomerDto customerDto = new CustomerDto(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCreateAt(),
                voucherDtoList
        );

        return customerDto;
    }
}

