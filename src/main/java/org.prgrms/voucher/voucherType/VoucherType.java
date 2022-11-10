package org.prgrms.voucher.voucherType;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.discountType.Amount;

public enum VoucherType {

  FIXED("1", (Amount amount) -> new FixedAmountVoucher(UUID.randomUUID(), amount),
      DiscountAmount::new),
  PERCENT("2", (Amount amount) -> new PercentDiscountVoucher(UUID.randomUUID(), amount),
      DiscountRate::new);

  private final String type;

  private final Function<Amount, Voucher> voucher;

  private final Function<String, Amount> amount;

  VoucherType(String type, Function<Amount, Voucher> voucher,
      Function<String, Amount> amount) {
    this.type = type;
    this.voucher = voucher;
    this.amount = amount;
  }

  public static VoucherType of(String choice) {
    return Stream.of(VoucherType.values())
        .filter(voucher -> voucher.type.equals(choice))
        .findAny()
        .orElseThrow(() -> new NoSuchVoucherTypeException(choice));
  }

  public static Voucher generateVoucher(VoucherType voucherType, Amount discount) {
    return voucherType.voucher.apply(discount);
  }

  public static Amount generateAmount(VoucherType voucherType, String value) {
    return voucherType.amount.apply(value);
  }

  public String getType() {
    return type;
  }
}
