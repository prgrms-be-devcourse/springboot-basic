package com.programmers.vouchermanagement.controller.rest;

import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/voucher")
public class RestVoucherController {
    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherDto.Response>> showAllVouchers() {
        final List<VoucherDto.Response> vouchers = voucherService.findAllVouchers().stream()
                .map(VoucherDto.Response::new).toList();
        return new ResponseEntity<>(vouchers, HttpStatus.OK);

    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherDto.Response> searchVoucher(@PathVariable String voucherId) {
        VoucherDto.Response voucher = new VoucherDto.Response(voucherService.findVoucherById(UUID.fromString(voucherId)));
        return new ResponseEntity<>(voucher, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<VoucherDto.Response> createVoucher(@ModelAttribute VoucherDto.CreateRequest voucherDto) {
        VoucherDto.Response voucher = new VoucherDto.Response(voucherService.createVoucher(voucherDto));
        return new ResponseEntity<>(voucher, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteVoucher(@RequestParam String voucherId) {
        voucherService.deleteVoucher(UUID.fromString(voucherId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
