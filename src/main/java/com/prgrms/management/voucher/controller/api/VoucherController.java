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

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/voucher")
@RequiredArgsConstructor
public class VoucherController {
    private final ResponseService responseService;
    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<SingleResult<Voucher>> createVoucher(
            @RequestBody VoucherRequest voucherRequest
    ) {
        return new ResponseEntity<>(responseService.getSingleResult(voucherService.createVoucher(voucherRequest)), OK);
    }

    @GetMapping
    public ResponseEntity<ListResult<Voucher>> findVouchersByVoucherTypeOrCreatedAt(
            @RequestParam(value = "voucherType", required = false) VoucherType voucherType,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "createdAt", required = false) LocalDate createdAt
    ) {
        return new ResponseEntity<>(
                responseService.getListResult(
                        voucherService.findAllByVoucherTypeOrCreatedAt(
                                voucherType, createdAt)), OK);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<SingleResult<Voucher>> findVoucherById(
            @PathVariable(value = "voucherId") String voucherId
    ) {
        return new ResponseEntity<>(responseService.getSingleResult(voucherService.findById(UUID.fromString(voucherId))), OK);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<SingleResult<Voucher>> deleteById(
            @PathVariable(value = "voucherId") String voucherId
    ) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return new ResponseEntity<>(responseService.getSingleResult(null), OK);
    }
}
