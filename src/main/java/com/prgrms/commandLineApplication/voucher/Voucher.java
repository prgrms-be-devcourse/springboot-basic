package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.discount.Discount;
import com.prgrms.commandLineApplication.discount.FixedDiscount;
import com.prgrms.commandLineApplication.discount.PercentDiscount;
import com.prgrms.commandLineApplication.validator.Validator;

import java.util.UUID;

public class Voucher {

  private final UUID voucherId;
  private final Discount discount;

  public Voucher(UUID voucherId, Discount discount) {
    Validator.checkId(voucherId);
    this.voucherId = voucherId;
    this.discount = discount;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public Discount getDiscount() {
    return discount;
  }

  public int supplyDiscountAmount() {
    return this.discount instanceof FixedDiscount
            ? ((FixedDiscount) discount).getDiscountAmount()
            : ((PercentDiscount) discount).getDiscountAmount();
  }

}
