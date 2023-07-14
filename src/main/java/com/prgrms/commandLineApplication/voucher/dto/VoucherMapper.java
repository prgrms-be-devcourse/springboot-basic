package com.prgrms.commandLineApplication.voucher.dto;

import com.prgrms.commandLineApplication.voucher.Voucher;

public class VoucherMapper {

  public static VoucherResponseDto mapToReposnse(Voucher voucher) {
    return new VoucherResponseDto(voucher.getVoucherId(), voucher.supplyDiscountType(), voucher.supplyDiscountAmount());
  }

}
