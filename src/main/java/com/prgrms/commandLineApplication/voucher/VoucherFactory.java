package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.voucher.discount.Discount;
import com.prgrms.commandLineApplication.voucher.discount.DiscountFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

  public static Voucher createVoucher(String voucherType, int discountAmount) {
    Discount discount = DiscountFactory.createDiscount(voucherType, discountAmount);
    return new Voucher(UUID.randomUUID(), discount);
  }

}
