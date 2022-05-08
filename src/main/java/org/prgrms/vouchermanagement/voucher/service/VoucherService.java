package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.customer.wallet.WalletRepository;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;
import org.prgrms.vouchermanagement.voucher.voucher.dto.UpdatedVoucher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

  public boolean issueVoucher(UUID customerId, UUID voucherId) {
    if(voucherRepository.checkExistenceById(voucherId) &&
      customerRepository.checkExistenceById(customerId)) {
      walletRepository.insert(customerId, voucherId);
      return true;
    }
    return false;
  }

  public UpdatedVoucher updateVoucher(UpdatedVoucher updatedVoucher) {
    voucherRepository.updateById(updatedVoucher.getVoucherId(), updatedVoucher.getReduction());
    return updatedVoucher;
  }

  public Voucher createVoucher(VoucherType voucherType, long reduction, LocalDateTime createdAt) {
    var newVoucher = VoucherFactory.createVoucher(voucherType, reduction, createdAt);
    voucherRepository.insert(newVoucher);
    return newVoucher;
  }

  public UUID deleteVoucher(UUID voucherId) {
    voucherRepository.deleteById(voucherId);
    return voucherId;
  }

  public List<Voucher> getVoucherList() {
    return voucherRepository.findAll();
  }

  public List<Customer> getCustomerList() {
    return customerRepository.findAll();
  }

  public Optional<Voucher> getVoucherById(UUID voucherId) {
    return voucherRepository.findById(voucherId);
  }

  public List<Voucher> getVoucherByVoucherType(VoucherType voucherType) {
    return voucherRepository.findByVoucherType(voucherType);
  }
}
