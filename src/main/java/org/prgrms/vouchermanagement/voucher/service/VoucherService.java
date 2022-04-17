package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.customer.wallet.WalletRepository;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.springframework.stereotype.Service;

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

//  public Voucher getVoucher(UUID voucherId) {
//    return voucherRepository
//      .findById(voucherId)
//      .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
//  }

  public boolean issueVoucher(Voucher voucher, Customer customer) {
    if(voucherRepository.findById(voucher.getVoucherID()).isPresent() &&
      customerRepository.findById(customer.getCustomerId()).isPresent()) {
      walletRepository.insert(customer.getCustomerId(), voucher.getVoucherID());
      return true;
    }

    return true;
  }
}
