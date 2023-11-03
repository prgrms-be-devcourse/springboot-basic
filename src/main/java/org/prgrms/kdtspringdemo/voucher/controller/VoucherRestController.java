package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherRequestDto;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    ResponseEntity<List<VoucherViewDto>> showAllVouchers() {
        List<VoucherViewDto> vouchers = voucherService.findAll().stream()
                .map(VoucherViewDto::new).toList();
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<Voucher> create(@ModelAttribute VoucherRequestDto voucherRequestDto) {
        VoucherTypeFunction voucherTypeFunction = VoucherTypeFunction.findByCode(voucherRequestDto.getVoucherPolicy());
        long amount = voucherRequestDto.getAmount();
        Voucher voucher = voucherService.createVoucher(voucherTypeFunction, amount);
        return new ResponseEntity<>(voucher, HttpStatus.CREATED);
    }

    @GetMapping("/{voucherId}")
    ResponseEntity<Voucher> findById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        return new ResponseEntity<>(voucher, HttpStatus.OK);
    }

    @GetMapping("/vouchers/policy")
    ResponseEntity<List<Voucher>> findByPolicy(@RequestParam String policy) {
        List<Voucher> vouchers = voucherService.findByPolicy(policy);
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }

    @DeleteMapping("/{voucherId}")
    void deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
    }

}
