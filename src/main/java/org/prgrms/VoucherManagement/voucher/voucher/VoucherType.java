package org.prgrms.VoucherManagement.voucher.voucher;

public enum VoucherType {
  FIXED_AMOUNT(1),
  PERCENT_DISCOUNT(2);

  private int selectedNumber;

  private VoucherType(int selectedNumber) {
    this.selectedNumber = selectedNumber;
  }

  private int getSelectedNumber() {
    return selectedNumber;
  }

  public static VoucherType fromInteger(int selectedNumber) {
    for(VoucherType type: values()) {
      if(type.getSelectedNumber() == selectedNumber) return type;
    }

    throw new RuntimeException("[VoucherTypeException] 존재하지 않는 번호입니다");
  }
}
