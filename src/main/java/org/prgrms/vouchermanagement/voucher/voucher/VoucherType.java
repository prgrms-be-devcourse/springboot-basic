package org.prgrms.vouchermanagement.voucher.voucher;

public enum VoucherType {
  FIXED_AMOUNT(1, "FixedAmountVoucher"),
  PERCENT_DISCOUNT(2, "PercentDiscountVoucher");

  private int selectedNumber;
  private String simpleClassName;

  VoucherType(int selectedNumber, String simpleClassName) {
    this.selectedNumber = selectedNumber;
    this.simpleClassName = simpleClassName;
  }

  int getSelectedNumber() {
    return selectedNumber;
  }

  String getSimpleClassName() {
    return simpleClassName;
  }
  /**
   *
   * @param voucher
   * @return db에 저장될 voucher의 타입입   */
  public static int toDbValue(Voucher voucher) {
    for(VoucherType type: values()) {
      if(type.getSimpleClassName().equals(voucher.getClass().getSimpleName())) return type.selectedNumber;
    }
    throw new RuntimeException("[VoucherTypeException] 존재하지 않는 클래스입니다");
  }

  public static VoucherType fromInstance(Voucher voucher) {
    for(VoucherType type: values()) {
      if(type.getSimpleClassName().equals(voucher.getClass().getSimpleName())) return type;
    }
    throw new RuntimeException("[VoucherTypeException] 존재하지 않는 클래스입니다");
  }

  public int toDbValue() {
    return selectedNumber;
  }

  public static VoucherType fromDbValue(int number) {
    for(VoucherType type: values()) {
      if(type.getSelectedNumber() == number) return type;
    }
    throw new RuntimeException("[VoucherTypeException] 존재하지 않는 번호입니다");
  }

}
