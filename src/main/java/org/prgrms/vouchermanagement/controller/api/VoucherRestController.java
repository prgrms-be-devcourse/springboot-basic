package org.prgrms.vouchermanagement.controller.api;

import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;
import org.prgrms.vouchermanagement.voucher.voucher.dto.NewVoucher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class VoucherRestController {
  /**
   * - [*]  전체 조회기능
   * - [*]  조건별 조회기능 (바우처 생성기간 및 특정 할인타입별)
   * - [*]  바우처 추가기능
   * - [*]  바우처 삭제기능
   * - [*]  바우처 아이디로 조회 기능
   */
  private final VoucherService voucherService;

  public VoucherRestController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  /**
   * @return 모든 Voucher 목록
   */
  @GetMapping("/api/v1/vouchers")
  public List<Voucher> getAllVouchers() {
    return voucherService.getVoucherList();
  }

  @GetMapping("/api/v1/voucher/{voucherId}")
  public ResponseEntity<Voucher> getVoucherByVoucherId(@RequestParam("voucherId") UUID voucherId) {
    var voucher = voucherService.getVoucherById(voucherId);
    return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/api/v1/vouchertype/{voucherType}")
  public List<Voucher> getVouchersByVoucherType(@RequestParam("type") VoucherType voucherType) {
    return voucherService.getVoucherByVoucherType(voucherType);
  }

  @DeleteMapping("/api/v1/voucher/{voucherId}")
  public void deleteVoucherByVoucherId(@RequestParam("voucherId") UUID voucherId) {
    voucherService.deleteVoucher(voucherId);
  }

  @PostMapping("/api/v1/create-voucher")
  public Voucher createVoucher(NewVoucher newVoucher) {
    Voucher voucher = voucherService.createVoucher(newVoucher);
    return voucher;
  }
}
