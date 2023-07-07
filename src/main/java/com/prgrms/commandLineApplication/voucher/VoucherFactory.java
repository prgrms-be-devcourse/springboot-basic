package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.voucher.discount.Discount;
import com.prgrms.commandLineApplication.voucher.discount.DiscountFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class VoucherFactory {

  public static Voucher of(String voucherType, int discountAmount) throws IOException {
    Discount discount = DiscountFactory.of(voucherType, discountAmount);
    return new Voucher(UUID.randomUUID(), discount);
  }

}
