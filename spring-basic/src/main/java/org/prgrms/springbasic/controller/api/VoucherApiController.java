package org.prgrms.springbasic.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.controller.api.request.VoucherRequest;
import org.prgrms.springbasic.controller.api.response.VoucherResponse;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.service.web.VoucherService;
import org.prgrms.springbasic.utils.exception.NoDatabaseChangeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.prgrms.springbasic.domain.voucher.VoucherType.valueOf;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_DELETED;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoucherApiController {

    private final VoucherService voucherService;

    @GetMapping("/v1/vouchers")
    public ResponseEntity<List<VoucherResponse>> showVouchers() {
        var voucherResponses =
                voucherService
                    .findVouchers()
                    .stream()
                    .map(VoucherResponse::of)
                    .toList();

        return ResponseEntity.ok(voucherResponses);
    }

    @GetMapping("/v1/vouchers/period")
    public ResponseEntity<List<VoucherResponse>> searchByPeriod(@RequestParam String from, @RequestParam String to) {
        var voucherResponses =
                voucherService
                    .findVoucherByPeriod(from, to)
                    .stream()
                    .map(VoucherResponse::of)
                    .toList();

        return ResponseEntity.ok(voucherResponses);
    }

    @GetMapping("/v1/{voucherType}/vouchers")
    public ResponseEntity<List<VoucherResponse>> searchByType(@PathVariable String voucherType) {
        var voucherResponses =
                voucherService
                    .findVoucherByVoucherType(valueOf(voucherType.toUpperCase())).stream()
                    .map(VoucherResponse::of).toList();

        return ResponseEntity.ok(voucherResponses);
    }

    @PostMapping("/v1/vouchers/new")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherRequest voucherRequest) {
        var newVoucher = voucherRequest.voucherType().createVoucher(voucherRequest.discountInfo());
        var savedVoucher = voucherService.createVoucher(newVoucher);
        var voucherResponse = VoucherResponse.of(savedVoucher);

        return ResponseEntity.ok(voucherResponse);
    }

    @DeleteMapping("/v1/{voucherId}/vouchers")
    public ResponseEntity<Boolean> removeVoucher(@PathVariable UUID voucherId) {
        var isDeleted = voucherService.removeVoucherById(voucherId);

        if (!isDeleted) {
            throw new NoDatabaseChangeException(NOT_DELETED.getMessage());
        }

        return ResponseEntity.ok(true);
    }


    @GetMapping("/v1/{voucherId}/vouchers/detail")
    public ResponseEntity<VoucherResponse> searchById(@PathVariable UUID voucherId) {
        Voucher retrievedVoucher = voucherService.findVoucherByVoucherId(voucherId);
        var voucherResponse = VoucherResponse.of(retrievedVoucher);

        return ResponseEntity.ok(voucherResponse);
    }
}