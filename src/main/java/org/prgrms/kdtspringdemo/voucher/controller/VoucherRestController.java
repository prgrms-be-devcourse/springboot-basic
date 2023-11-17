package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.dto.VoucherRequestDto;
import org.prgrms.kdtspringdemo.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherViewDto>> showAllVouchers(@RequestParam(name = "id", required = false) Optional<UUID> voucherId, @RequestParam(name = "policy", required = false) Optional<String> policy) {

        if (voucherId.isPresent()) {
            VoucherViewDto voucher = voucherService.findById(voucherId.get());
            if (voucher != null) {
                return new ResponseEntity<>(Collections.singletonList(voucher), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else if (policy.isPresent()) {
            List<VoucherViewDto> vouchers = voucherService.findByPolicy(policy.get());
            return new ResponseEntity<>(vouchers, HttpStatus.OK);
        } else {
            List<VoucherViewDto> vouchers = voucherService.getVoucherViewDtoList();
            return new ResponseEntity<>(vouchers, HttpStatus.OK);
        }
    }

    @PostMapping
    ResponseEntity<VoucherViewDto> create(@ModelAttribute VoucherRequestDto voucherRequestDto) {
        VoucherTypeFunction voucherTypeFunction = VoucherTypeFunction.findByCode(voucherRequestDto.getVoucherPolicy());
        long amount = voucherRequestDto.getAmount();
        VoucherViewDto voucher = voucherService.createVoucher(voucherTypeFunction, amount);
        return new ResponseEntity<>(voucher, HttpStatus.CREATED);
    }

    @DeleteMapping("/{voucherId}")
    void deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
    }

}
