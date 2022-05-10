package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.Duration;
import com.waterfogsw.voucher.voucher.dto.RequestVoucherDto;
import com.waterfogsw.voucher.voucher.dto.ResponseVoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseVoucherDto addVoucher(@RequestBody RequestVoucherDto request) {
        final var savedVoucher = voucherService.saveVoucher(request.toDomain());
        return ResponseVoucherDto.from(savedVoucher);
    }

    @GetMapping
    public List<ResponseVoucherDto> findVoucherAll(Duration duration, VoucherType voucherType) {
        System.out.println(duration);
        System.out.println(voucherType);
        if (duration.isNull() && voucherType == null) {
            return voucherService.findAllVoucher()
                    .stream()
                    .map(ResponseVoucherDto::from)
                    .toList();
        }

        if (duration.isNull()) {
            return voucherService.findByType(voucherType)
                    .stream()
                    .map(ResponseVoucherDto::from)
                    .toList();
        }

        if (voucherType == null) {
            return voucherService.findByDuration(duration)
                    .stream()
                    .map(ResponseVoucherDto::from)
                    .toList();
        }

        return voucherService.findByTypeDuration(voucherType, duration)
                .stream()
                .map(ResponseVoucherDto::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseVoucherDto findVoucherById(@PathVariable("id") long id) {
        final var voucher = voucherService.findVoucherById(id);
        return ResponseVoucherDto.from(voucher);
    }

    @DeleteMapping("/{id}")
    public void deleteVoucherById(@PathVariable("id") long id) {
        voucherService.deleteVoucherById(id);
    }
}
