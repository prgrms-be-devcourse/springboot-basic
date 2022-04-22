package org.prgrms.vouchermanagement.customer.service;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.customer.wallet.WalletRepository;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> getCustomerList() {
    return customerRepository.findAll();
  }
}
