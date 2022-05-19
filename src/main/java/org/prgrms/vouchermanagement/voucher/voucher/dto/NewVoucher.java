package org.prgrms.vouchermanagement.voucher.voucher.dto;

import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;

public class NewVoucher {
  private long reduction;
  private VoucherType voucherType;

  public NewVoucher(long reduction, VoucherType voucherType) {
    this.reduction = reduction;
    this.voucherType = voucherType;
  }

  public long getReduction() {
    return reduction;
  }

  public void setReduction(long reduction) {
    this.reduction = reduction;
  }

  public VoucherType getVoucherType() {
    return voucherType;
  }
}
