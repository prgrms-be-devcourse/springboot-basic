package org.prgrms.java.controller.api;

import org.prgrms.java.domain.voucher.CreateVoucherRequest;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("vouchers")
    @ResponseBody
    public ResponseEntity<List<Voucher>> findVouchers(
            @RequestParam(required = false) String owner,
            @RequestParam(required = false) boolean expired) {
        if (owner != null) {
            return new ResponseEntity<>(voucherService.getVoucherByOwnerId(owner), HttpStatus.OK);
        }
        if (expired) {
            return new ResponseEntity<>(voucherService.getAllExpiredVouchers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(voucherService.getAllVouchers(), HttpStatus.OK);
    }

    @GetMapping("voucher/{voucherId}")
    @ResponseBody
    public ResponseEntity<Voucher> findVoucherById(@PathVariable("voucherId") String voucherId) {
        return new ResponseEntity<>(voucherService.getVoucherById(voucherId), HttpStatus.OK);
    }

    @PostMapping("voucher")
    @ResponseBody
    public ResponseEntity<Voucher> createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        return new ResponseEntity<>(voucherService.saveVoucher(
                createVoucherRequest.getOwnerId(),
                createVoucherRequest.getType(),
                createVoucherRequest.getAmount(),
                createVoucherRequest.getExpiredAt()), HttpStatus.OK);
    }

    @DeleteMapping("voucher/{voucherId}")
    public ResponseEntity<HttpStatus> deleteVoucherById(@PathVariable("voucherId") String voucherId) {
        voucherService.deleteVoucher(voucherId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
