package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.customer.wallet.WalletRepository;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;
  private final CustomerRepository customerRepository;
  private final WalletRepository walletRepository;

  public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository, WalletRepository walletRepository) {
    this.voucherRepository = voucherRepository;
    this.customerRepository = customerRepository;
    this.walletRepository = walletRepository;
  }

  public boolean issueVoucher(UUID voucherId, UUID customerId) {
    if(voucherRepository.findById(voucherId).isPresent() &&
      customerRepository.findById(customerId).isPresent()) {
      walletRepository.insert(customerId, voucherId);
      return true;
    }
    return false;
  }

  public List<Voucher> getVoucherList() {
    return voucherRepository.findAll();
  }

  public List<Customer> getCustomerList() {
    return customerRepository.findAll();
  }
}
