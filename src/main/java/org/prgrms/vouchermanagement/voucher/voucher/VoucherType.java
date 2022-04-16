package org.prgrms.vouchermanagement.voucher.voucher;

public enum VoucherType {
  FIXED_AMOUNT(1, "FixedAmountVoucher"),
  PERCENT_DISCOUNT(2, "PercentDiscountVoucher");

  private int selectedNumber;
  private String simpleClassName;

  private VoucherType(int selectedNumber, String simpleClassName) {
    this.selectedNumber = selectedNumber;
    this.simpleClassName = simpleClassName;
  }

  int getSelectedNumber() {
    return selectedNumber;
  }

  String getSimpleClassName() {
    return simpleClassName;
  }

  public static VoucherType fromString(String className) {
    for(VoucherType type: values()) {
      if(type.getSimpleClassName().equals(className)) return type;
    }
    throw new RuntimeException("[VoucherTypeException] 존재하지 않는 클래스입니다");
  }

  public static int toDbValue(Voucher voucher) {
    for(VoucherType type: values()) {
      if(type.getSimpleClassName().equals(voucher.getClass().getSimpleName())) return type.selectedNumber;
    }
    throw new RuntimeException("[VoucherTypeException] 존재하지 않는 클래스입니다");
  }

  public static VoucherType fromDbValue(int number) {
    for(VoucherType type: values()) {
      if(type.getSelectedNumber() == number) return type;
    }
    throw new RuntimeException("[VoucherTypeException] 존재하지 않는 번호입니다");
  }

}
