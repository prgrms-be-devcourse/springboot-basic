package com.example.commandlineapplication.domain.voucher.model;

import java.util.UUID;

public abstract class Voucher {

  public abstract UUID getVoucherId();

  public abstract double discountedPrice(long beforeDiscount);

  public abstract VoucherType getVoucherType();
}
