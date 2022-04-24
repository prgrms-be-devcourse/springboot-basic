package org.programs.kdt.Wallet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class WalletTest {

  @Test
  @DisplayName("Voucher와 Customer로 wallet을 만들 수 있다.")
  public void testCreateWallet() {
    VoucherType voucherType = VoucherType.PERCENT;
    Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), 20L, LocalDateTime.now());
    Customer customer = new Customer(UUID.randomUUID(), "choi", "choi@naver.com");
    Wallet wallet = new Wallet(voucher, customer, UUID.randomUUID(), LocalDateTime.now());

    assertThat(wallet.getCustomerEmail(), is(customer.getEmail()));
    assertThat(wallet.getCustomerId(), is(customer.getCustomerId()));
    assertThat(wallet.getCustomerName(), is(customer.getName()));

    assertThat(wallet.getVoucherId(), is(voucher.getVoucherId()));
    assertThat(wallet.getVoucherType(), is(voucherType));
    assertThat(wallet.getVoucherValue(), is(voucher.getValue()));
  }
}
