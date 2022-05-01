package org.prgrms.vouchermanagement.controller.api;

import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoucherRestController {
  /**
   * - [*]  전체 조회기능
   * - [ ]  조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
   * - [ ]  바우처 추가기능
   * - [ ]  바우처 삭제기능
   * - [ ]  바우처 아이디로 조회 기능
   */
  private final VoucherService voucherService;

  public VoucherRestController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("/api/v1/vouchers")
  public List<Voucher> getAllVouchers() {
    return voucherService.getVoucherList();
  }
}
