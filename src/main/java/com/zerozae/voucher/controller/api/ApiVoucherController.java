package com.zerozae.voucher.controller.api;

import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherCondition;
import com.zerozae.voucher.dto.voucher.VoucherCreateRequest;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.service.voucher.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.zerozae.voucher.validator.InputValidator.validateInputUuid;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vouchers")
public class ApiVoucherController {

    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity createVoucher(@Valid @RequestBody VoucherCreateRequest voucherCreateRequest) {
        return new ResponseEntity(voucherService.createVoucher(voucherCreateRequest), CREATED);
    }

    @GetMapping
    public ResponseEntity findAllVouchers() {
        return new ResponseEntity(voucherService.findAllVouchers(), OK);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity findVoucherById(@PathVariable("voucherId") String voucherId) {
        validateInputUuid(voucherId);
        return new ResponseEntity(voucherService.findById(UUID.fromString(voucherId)), OK);
    }

    @GetMapping("/condition")
    public ResponseEntity findVoucherByCondition(@Valid @RequestBody VoucherCondition condition) {
        return new ResponseEntity(voucherService.findVoucherByCondition(condition), OK);
    }

    @PatchMapping("/{voucherId}")
    public ResponseEntity updateVoucher(@PathVariable("voucherId") String voucherId, @Valid @RequestBody VoucherUpdateRequest voucherUpdateRequest) {
        validateInputUuid(voucherId);
        UseStatusType.of(voucherUpdateRequest.getUseStatusType());
        return new ResponseEntity(voucherService.update(UUID.fromString(voucherId), voucherUpdateRequest), OK);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity deleteVoucherById(@PathVariable("voucherId") String voucherId) {
        validateInputUuid(voucherId);
        voucherService.deleteById(UUID.fromString(voucherId));
        return ResponseEntity.ok("완료 되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity deleteAllVouchers() {
        voucherService.deleteAll();
        return ResponseEntity.ok("완료 되었습니다.");
    }
}
