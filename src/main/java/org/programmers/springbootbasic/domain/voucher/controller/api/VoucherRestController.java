package org.programmers.springbootbasic.domain.voucher.controller.api;

import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.voucher.service.VoucherService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class VoucherRestController {

    private final VoucherService voucherService;


    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping(value = "/vouchers")
    @ResponseBody
    public ResponseEntity<List<Voucher>> findVouchers() {
        List<Voucher> vouchers = voucherService.findAll();
        return ResponseEntity.ok(vouchers);
    }


    @GetMapping(value = "/vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<Voucher> findVoucher(@PathVariable("voucherId") UUID voucherId) {
        Voucher voucher = voucherService.findOneById(voucherId);
        return ResponseEntity.ok(voucher);
    }

    @GetMapping("/vouchers/filter")
    @ResponseBody
    public ResponseEntity<List<Voucher>> findVoucherByType(@RequestParam("voucherType") String voucherType) {
        List<Voucher> vouchers = voucherService.findByType(voucherType);
        return ResponseEntity.ok(vouchers);
    }


    @PostMapping("/vouchers")
    @ResponseBody
    public ResponseEntity<Void> createVoucher(@RequestBody VoucherInputDto voucherInputDto) {
        voucherService.createVoucher(voucherInputDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<Void> deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return ResponseEntity.ok().build();
    }

}
