package org.programs.kdt.Wallet;

import lombok.Getter;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Wallet {

  // Wallet
  private final UUID walletId;
  private final LocalDateTime createdAt;
  // Voucher
  private UUID voucherId;
  private Long voucherValue;
  private VoucherType voucherType;
  // Customer
  private UUID customerId;
  private String customerName;
  private String customerEmail;

  public Wallet(Voucher voucher, Customer customer, UUID walletId, LocalDateTime createdAt) {
    this.voucherId = voucher.getVoucherId();
    this.customerId = customer.getCustomerId();
    this.walletId = walletId;
    this.voucherValue = voucher.getValue();
    this.customerName = customer.getName();
    this.customerEmail = customer.getEmail();
    this.voucherType = voucher.getVoucherType();
    this.createdAt = createdAt;
  }
}
