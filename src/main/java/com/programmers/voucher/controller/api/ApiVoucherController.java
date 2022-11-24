package com.programmers.voucher.controller.api;

import com.programmers.voucher.dto.VoucherRegisterForm;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.voucher.Voucher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
public class ApiVoucherController {
    private final VoucherService voucherService;

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping("/api/vouchers")
    public ResponseEntity<List<Voucher>> findAll() {

        List<Voucher> vouchers = voucherService.findAll();

        return new ResponseEntity<>(vouchers, OK);
    }

    @PostMapping("/api/vouchers")
    public ResponseEntity<Voucher> createVoucher(@RequestBody VoucherRegisterForm registerForm) {

        String type = registerForm.getType();
        String value = registerForm.getValue();

        Voucher voucher = voucherService.register(type, value);
        return new ResponseEntity<>(voucher, OK);
    }

    @DeleteMapping("/api/vouchers/{id}")
    public ResponseEntity<Voucher> deleteVoucher(@RequestBody @PathVariable("id") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);

        return new ResponseEntity<>(OK);
    }

    @GetMapping("/api/vouchers/{id}")
    public ResponseEntity<Voucher> findVoucher(@RequestBody @PathVariable("id") UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return new ResponseEntity<>(voucher, OK);
    }

    @GetMapping("/api/vouchers/type/{type}")
    public ResponseEntity<List<Voucher>> findByType(@RequestBody @PathVariable("type") String type) {
        List<Voucher> vouchers = voucherService.getTypeVoucher(type);

        return new ResponseEntity<>(vouchers, OK);
    }
}