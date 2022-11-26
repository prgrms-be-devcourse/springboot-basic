package com.example.springbootbasic.controller.voucher;

import com.example.springbootbasic.controller.request.CreateVoucherRequest;
import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.dto.voucher.VoucherDto;
import com.example.springbootbasic.service.voucher.JdbcVoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vouchers")
public class RestVoucherController {

    private final JdbcVoucherService voucherService;

    public RestVoucherController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseBody<List<VoucherDto>> getAllVouchers() {
        List<VoucherDto> result = voucherService.findAllVouchers()
                .stream()
                .map(VoucherDto::newInstance)
                .toList();
        if (result.isEmpty()) {
            return ResponseBody.fail(result);
        }
        return ResponseBody.success(result);
    }

    @GetMapping("/search")
    public ResponseBody<List<VoucherDto>> findAllVouchersBy(
            @RequestParam(required = false) Long voucherId,
            @RequestParam(required = false) Optional<String> voucherType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime findStartAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime findEndAt
    ) {
        VoucherType paramVoucherType = null;
        if (voucherType.isPresent()) {
            paramVoucherType = VoucherType.of(voucherType.get());
        }
        List<VoucherDto> findVouchers = voucherService
                .findVouchersBy(voucherId, paramVoucherType, findStartAt, findEndAt)
                .stream()
                .map(VoucherDto::newInstance)
                .toList();
        if (findVouchers.isEmpty()) {
            return ResponseBody.fail(findVouchers);
        }
        return ResponseBody.success(findVouchers);
    }

    @PostMapping
    public ResponseBody<VoucherDto> addVoucher(@RequestBody CreateVoucherRequest request) {
        long discountValue = request.discountValue();
        VoucherType voucherType = VoucherType.of(request.voucherType());
        LocalDateTime startAt = request.startAt();
        LocalDateTime endAt = request.endAt();
        VoucherDto savedVoucher = VoucherDto.newInstance(voucherService.saveVoucher(
                VoucherFactory.of(discountValue, voucherType, LocalDateTime.now(), startAt, endAt)));
        if (savedVoucher.isEmpty()) {
            return ResponseBody.fail(savedVoucher);
        }
        return ResponseBody.success(savedVoucher);
    }

    @DeleteMapping
    public ResponseBody<Boolean> deleteVoucher(@RequestParam Long voucherId) {
        boolean isDeleted = voucherService.deleteVoucherById(voucherId);
        if (isDeleted) {
            return ResponseBody.success(true);
        }
        return ResponseBody.fail(false);
    }
}
