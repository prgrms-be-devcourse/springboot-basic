package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.dto.voucher.VoucherRequest.VoucherCreateRequest;
import static com.prgrms.vouchermanager.dto.voucher.VoucherRequest.VoucherFindByConditionRequest;
import static com.prgrms.vouchermanager.dto.voucher.VoucherResponse.VoucherDeleteResponse;
import static com.prgrms.vouchermanager.dto.voucher.VoucherResponse.VoucherDetailResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherRestController {
    private final VoucherService service;

    @GetMapping
    public ResponseEntity<List<VoucherDetailResponse>> vouchers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherDetailResponse> voucher(@PathVariable UUID voucherId) {
        return new ResponseEntity<>(service.findById(voucherId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<VoucherDetailResponse> create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        VoucherDetailResponse response = service.create(voucherCreateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{voucherId}/delete")
    public ResponseEntity<VoucherDeleteResponse> delete(@PathVariable UUID voucherId) {
        service.delete(voucherId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findByCondition")
    public ResponseEntity<List<VoucherDetailResponse>> findByCondition(@RequestBody VoucherFindByConditionRequest request) {
        return new ResponseEntity<>(service.findByCondition(request), HttpStatus.OK);
    }
}
