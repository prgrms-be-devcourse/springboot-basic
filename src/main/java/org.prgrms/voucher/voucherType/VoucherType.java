package org.prgrms.voucher.voucherType;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.discountType.Discount;

public enum VoucherType {

  FIXED("1", (Discount amount) -> new FixedAmountVoucher(UUID.randomUUID(), amount),
      DiscountAmount::new),
  PERCENT("2", (Discount percent) -> new PercentDiscountVoucher(UUID.randomUUID(), percent),
      DiscountRate::new);

  private final String type;

  private final Function<Discount, Voucher> voucher;

  private final Function<String, Discount> discount;

  VoucherType(String type, Function<Discount, Voucher> voucher,
      Function<String, Discount> discount) {
    this.type = type;
    this.voucher = voucher;
    this.discount = discount;
  }

  public static VoucherType of(String choice) {
    return Stream.of(VoucherType.values())
        .filter(voucher -> voucher.type.equals(choice))
        .findFirst()
        .orElseThrow(() -> new NoSuchVoucherTypeException(choice));
  }

  public static Voucher generateVoucher(VoucherType voucherType, Discount discount) {
    return voucherType.voucher.apply(discount);
  }

  public static Discount generateDiscount(VoucherType voucherType, String value) {
    return voucherType.discount.apply(value);
  }
}
