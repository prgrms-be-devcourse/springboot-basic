package org.prgrms.kdtspringorder.voucher.domain;

import java.util.UUID;
import org.prgrms.kdtspringorder.voucher.enums.VoucherPolicy;

public class Voucher {
  private UUID voucherId;
  private final VoucherPolicy voucherPolicy;

  public Voucher(VoucherPolicy voucherPolicy) {
    this.voucherPolicy = voucherPolicy;
  }

  // Voucher의 ID를 Repository에 저장 될 때 생성되도록 해주고 싶었습니다.
  // voucherId를 final로 선언하여 Voucher
  // 불변으로 설계하고자 하였으나 constructor로 만들어주게 되면
  // 생성자로 ID를 할당하는 방법밖에 없기 때문에 Repository에 저장될 때 ID를 만들어 주고자 하면 Voucer 객체를 Repository에서 생성 해야한다.
  // 위와 같은 문제점 때문에 아래와 같이 설계하였습니다.

  public void assignId(final UUID id){
    if(this.voucherId != null) {
      throw new RuntimeException("이미 ID가 할당된 Voucher입니다.");
    }
    this.voucherId = id;
  }

  public UUID getId() {
    return voucherId;
  }

  public long discount(long originalPrice){
    return voucherPolicy.discount(originalPrice);
  }

  public String getVoucherTypeInString(){
    return this.voucherPolicy.getPolicyType();
  }

  @Override
  public String toString() {
    return "Voucher{" +
        "voucherId=" + voucherId +
        ", voucherPolicy=" + voucherPolicy.getPolicyType() +
        '}';
  }
}
