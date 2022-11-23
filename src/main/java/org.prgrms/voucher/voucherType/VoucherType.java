package org.prgrms.voucher.voucherType;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;

public enum VoucherType {

  FIXED(1, FixedAmountVoucher::new, DiscountAmount::new),
  PERCENT(2, PercentDiscountVoucher::new, DiscountRate::new);

  private final int type;

  private final BiFunction<UUID ,Amount, Voucher> voucher;

  private final Function<Long , Amount> amount;

  VoucherType(int type, BiFunction<UUID, Amount, Voucher> voucher,
      Function<Long, Amount> amount) {
    this.type = type;
    this.voucher = voucher;
    this.amount = amount;
  }

  public static VoucherType of(int choice) {
    return Stream.of(VoucherType.values())
        .filter(voucher -> voucher.type == choice)
        .findAny()
        .orElseThrow(() -> new NoSuchVoucherTypeException(choice));
  }

  public static VoucherType of(String choice) {
    return Stream.of(VoucherType.values())
        .filter(voucher -> choice.contains(voucher.name()))
        .findAny()
        .orElseThrow(() -> new NoSuchVoucherTypeException(choice));
  }

  public Voucher generateVoucher(Amount discount) {
    return this.voucher.apply(UUID.randomUUID(),discount);
  }

  public Voucher generateVoucherWithId(UUID id, Amount discount) {
    return this.voucher.apply(id, discount);
  }

  public Amount generateAmount(long value) {
    return this.amount.apply(value);
  }

  public int getType() {
    return type;
  }

}