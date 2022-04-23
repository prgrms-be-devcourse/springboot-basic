package com.prgrms.management.voucher.controller.api;


import com.prgrms.management.config.common.ListResult;
import com.prgrms.management.config.common.SingleResult;
import com.prgrms.management.config.common.service.ResponseService;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherController {
    private final ResponseService responseService;
    private final VoucherService voucherService;

    @GetMapping
    public ResponseEntity<ListResult<Voucher>> findVouchers() {
        return new ResponseEntity<>(responseService.getListResult(voucherService.findAll()), OK);
    }

    @GetMapping("/filtering")
    public ResponseEntity<ListResult<Voucher>> findVouchersByVoucherTypeOrCreatedAt(
            @RequestParam(value = "voucherType", required = false) VoucherType voucherType,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            @RequestParam(value = "createdAt", required = false) LocalDateTime createdAt
    ) {
        return new ResponseEntity<>(
                responseService.getListResult(
                        voucherService.findAllByVoucherTypeOrCreatedAt(voucherType, createdAt)), OK);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<SingleResult<Voucher>> findVoucherById(
            @PathVariable(value = "voucherId") String voucherId
    ) {
        return new ResponseEntity<>(responseService.getSingleResult(voucherService.findById(UUID.fromString(voucherId))), OK);
    }

    @PostMapping
    public ResponseEntity<SingleResult<Voucher>> createVoucher(
            @RequestBody VoucherRequest voucherRequest
    ) {
        return new ResponseEntity<>(responseService.getSingleResult(voucherService.createVoucher(voucherRequest)), OK);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<SingleResult<Voucher>> deleteById(
            @PathVariable(value = "voucherId") String voucherId
    ) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return new ResponseEntity<>(responseService.getSingleResult(null), OK);
    }
}
