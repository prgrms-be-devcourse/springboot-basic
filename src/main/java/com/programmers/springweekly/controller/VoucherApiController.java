package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherResponse;
import com.programmers.springweekly.service.VoucherService;
import com.programmers.springweekly.util.validator.VoucherValidator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class VoucherApiController {

    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<VoucherResponse> save(@Validated VoucherCreateRequest voucherCreateRequest) {
        VoucherValidator.validateVoucher(
                voucherCreateRequest.getVoucherType(),
                String.valueOf(voucherCreateRequest.getDiscountAmount())
        );

        VoucherResponse voucherResponse = voucherService.save(voucherCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(voucherResponse);
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> getFindAll() {
        VoucherListResponse voucherListResponse = voucherService.findAll();

        return ResponseEntity.ok(voucherListResponse.getVoucherList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponse> findById(@PathVariable("id") UUID voucherId) {
        VoucherResponse voucherResponse = voucherService.findById(voucherId);

        return ResponseEntity.ok(voucherResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID voucherId) {
        boolean isExistVoucherId = voucherService.existById(voucherId);

        if (!isExistVoucherId) {
            throw new NoSuchElementException("사용자가 삭제하려는 바우처 " + voucherId + "는 없는 ID입니다.");
        }

        voucherService.deleteById(voucherId);

        return ResponseEntity.noContent().build();
    }

}
