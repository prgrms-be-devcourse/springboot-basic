package com.prgrms.voucher_manage.domain.customer.service;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.repository.CustomerRepository;
import com.prgrms.voucher_manage.util.OutputUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final OutputUtil outputUtil;
    private final CustomerRepository customerRepository;

    public void showCustomerBlackList() {
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(outputUtil::printCustomerInfo);
    }
}
