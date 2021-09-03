package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface CustomerVoucherService extends CustomerService {
    Optional<Customer> findCustomerByVoucher(long voucherId);
    List<Voucher> findAllVoucherByCustomer(long customerId);
}
