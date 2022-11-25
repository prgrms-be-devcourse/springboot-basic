package com.programmers.voucher.controller.api;

import com.programmers.voucher.dto.DateTypeDto;
import com.programmers.voucher.dto.VoucherRegisterForm;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/vouchers")
public class ApiVoucherController {
    private final VoucherService voucherService;
    private Logger log = LoggerFactory.getLogger(ApiVoucherController.class);

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping
    public ResponseEntity<List<Voucher>> findAll() {

        List<Voucher> vouchers = voucherService.findAll();

        return new ResponseEntity<>(vouchers, OK);
    }

    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@RequestBody VoucherRegisterForm registerForm) {

        String type = registerForm.getType();
        String value = registerForm.getValue();

        Voucher voucher = voucherService.register(type, value);
        return new ResponseEntity<>(voucher, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> findVoucher(@PathVariable("id") UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return new ResponseEntity<>(voucher, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Voucher> deleteVoucher(@PathVariable("id") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);

        return new ResponseEntity<>(OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Voucher>> findByType(@PathVariable("type") String type) {
        List<Voucher> vouchers = voucherService.getTypeVoucher(type);

        return new ResponseEntity<>(vouchers, OK);
    }

    @GetMapping("/time")
    public ResponseEntity<List<Voucher>> findByDateTime(@ModelAttribute DateTypeDto dateTypeDto) {
        LocalDateTime from = dateTypeDto.getFrom();
        LocalDateTime to = dateTypeDto.getTo();

        List<Voucher> vouchers = voucherService.findVoucherByPeriod(from, to);
        return new ResponseEntity<>(vouchers, OK);
    }
}