package org.programmers.devcourse.voucher.engine.voucher.controller;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoucherRegistrationDto {

  @NotNull
  private String voucherType;
  @NotNull
  private long discountDegree;

}
