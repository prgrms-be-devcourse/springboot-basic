package com.example.commandlineapplication.domain.voucher.controller;

import com.example.commandlineapplication.domain.voucher.VoucherType;
import com.example.commandlineapplication.domain.voucher.dto.request.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.dto.response.VoucherResponse;
import com.example.commandlineapplication.domain.voucher.service.VoucherService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class VoucherRestController {

  private final VoucherService voucherService;

  @PostMapping(path = "/vouchers")
  public ResponseEntity<String> createVoucher(
      @Valid @RequestBody VoucherCreateRequest voucherCreateRequest) {

    voucherService.createVoucher(voucherCreateRequest.getVoucherType(),
        voucherCreateRequest.getDiscountAmount());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body("voucher가 생성되었습니다. " + voucherCreateRequest.getVoucherId()
            + voucherCreateRequest.getVoucherType());
  }

  @GetMapping("/vouchers/list")
  public ResponseEntity<List<VoucherResponse>> getVouchers() {

    return ResponseEntity.status(HttpStatus.OK)
        .body(voucherService.findVouchers());
  }

  @GetMapping("/vouchers/{voucherId}")
  public ResponseEntity<VoucherResponse> getVoucher(
      @Valid @PathVariable String voucherId) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(voucherService.getVoucher(voucherId));
  }

  @GetMapping("/vouchers/type/{voucherType}")
  public ResponseEntity<List<VoucherResponse>> getVouchersByVoucherType(
      @Valid @PathVariable VoucherType voucherType) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(voucherService.getVouchersByVoucherType(voucherType));
  }

  @DeleteMapping("/vouchers/{voucherId}")
  public ResponseEntity<String> deleteVoucher(@Valid @PathVariable String voucherId) {

    voucherService.deleteVoucher(UUID.fromString(voucherId));

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body("삭제되었습니다.");
  }
}
