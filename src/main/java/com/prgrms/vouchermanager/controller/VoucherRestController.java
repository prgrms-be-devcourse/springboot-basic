package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.dto.VoucherResponse;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.prgrms.vouchermanager.dto.VoucherRequest.*;
import static com.prgrms.vouchermanager.dto.VoucherResponse.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherRestController {
    private final VoucherService service;

    @GetMapping
    public ResponseEntity<VoucherListResponse> vouchers() {
        return new ResponseEntity<>(new VoucherListResponse(service.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherDetailResponse> voucher(@PathVariable UUID voucherId) {
        return new ResponseEntity<>(toDetailVoucher(service.findById(voucherId)), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<VoucherDetailResponse> create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = service.create(voucherCreateRequest);
        return new ResponseEntity<>(VoucherResponse.toDetailVoucher(voucher), HttpStatus.OK);
    }

    @GetMapping("/{voucherId}/delete")
    public ResponseEntity<VoucherDeleteResponse> delete(@PathVariable UUID voucherId) {
        return new ResponseEntity<>(new VoucherDeleteResponse(service.delete(voucherId)),
                HttpStatus.OK);
    }

    @GetMapping("/findByCondition")
    public ResponseEntity<VoucherListResponse> findByCondition(@RequestBody VoucherFindByConditionRequest request) {
        return new ResponseEntity<>(new VoucherListResponse(service.findByCondition(request)), HttpStatus.OK);
    }
}
