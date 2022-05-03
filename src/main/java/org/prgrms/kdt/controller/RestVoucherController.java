package org.prgrms.kdt.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.dto.VoucherRequest;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vouchers")
public class RestVoucherController {

  private final VoucherService voucherService;

  public RestVoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping
  public ResponseEntity<List<Voucher>> getVouchers(
      @RequestParam(required = false) Integer voucherType,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAt,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt) {
    return ResponseEntity.ok(voucherService.findVouchers(voucherType, startAt, endAt));
  }

  @GetMapping("/{voucherId}")
  public ResponseEntity<Voucher> getVoucher(@PathVariable UUID voucherId) {
    return ResponseEntity.ok(voucherService.findById(voucherId));
  }

  @PostMapping
  public ResponseEntity<Voucher> createVoucher(
      @RequestBody VoucherRequest.CreateRequest createRequest) {
    var voucherDto = VoucherDto.of(createRequest);
    var voucher = voucherService.create(voucherDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(voucher);
  }

  @PatchMapping("/{voucherId}")
  public ResponseEntity<Voucher> assignVoucher(@PathVariable UUID voucherId,
      @RequestBody VoucherRequest.AssignRequest assignRequest) {
    var voucher = voucherService.assign(voucherId, assignRequest.customerId());
    return ResponseEntity.ok(voucher);
  }

  @DeleteMapping("/{voucherId}")
  public ResponseEntity<Void> deleteVoucher(@PathVariable UUID voucherId) {
    voucherService.deleteById(voucherId);
    return ResponseEntity.ok().build();
  }
}