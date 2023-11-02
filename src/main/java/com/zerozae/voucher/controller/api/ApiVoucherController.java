package com.zerozae.voucher.controller.api;

import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.dto.voucher.VoucherCondition;
import com.zerozae.voucher.dto.voucher.VoucherCreateRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
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

import java.util.List;
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
    public ResponseEntity<VoucherResponse> createVoucher(@Valid @RequestBody VoucherCreateRequest voucherCreateRequest) {
        return ResponseEntity.status(CREATED).body(voucherService.createVoucher(voucherCreateRequest));
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> findAllVouchers() {
        return ResponseEntity.status(OK).body(voucherService.findAllVouchers());
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> findVoucherById(@PathVariable("voucherId") String voucherId) {
        validateInputUuid(voucherId);
        return ResponseEntity.status(OK).body(voucherService.findById(UUID.fromString(voucherId)));
    }

    @GetMapping("/condition")
    public ResponseEntity<List<VoucherResponse>> findVoucherByCondition(@Valid @RequestBody VoucherCondition condition) {
        return ResponseEntity.status(OK).body(voucherService.findVoucherByCondition(condition));
    }

    @PatchMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> updateVoucher(@PathVariable("voucherId") String voucherId, @Valid @RequestBody VoucherUpdateRequest voucherUpdateRequest) {
        validateInputUuid(voucherId);
        return ResponseEntity.status(OK).body(voucherService.update(UUID.fromString(voucherId), voucherUpdateRequest));
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<String> deleteVoucherById(@PathVariable("voucherId") String voucherId) {
        validateInputUuid(voucherId);
        voucherService.deleteById(UUID.fromString(voucherId));
        return ResponseEntity.status(OK).body("완료 되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllVouchers() {
        voucherService.deleteAll();
        return ResponseEntity.status(OK).body("완료 되었습니다.");
    }
}
