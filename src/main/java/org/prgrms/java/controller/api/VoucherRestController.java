package org.prgrms.java.controller.api;

import org.prgrms.java.domain.voucher.CreateVoucherRequest;
import org.prgrms.java.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("vouchers")
    @ResponseBody
    public ResponseEntity<?> findVouchersAPI() {
        return new ResponseEntity<>(voucherService.getAllVouchers(), HttpStatus.OK);
    }

    @GetMapping("vouchers/owner/{customerId}")
    @ResponseBody
    public ResponseEntity<?> findVouchersByOwnerAPI(@PathVariable("customerId") String customerId) {
        return new ResponseEntity<>(voucherService.getVoucherByOwnerId(customerId), HttpStatus.OK);
    }

    @GetMapping("vouchers/expired")
    @ResponseBody
    public ResponseEntity<?> findExpiredVouchersAPI() {
        return new ResponseEntity<>(voucherService.getAllExpiredVouchers(), HttpStatus.OK);
    }

    @GetMapping("vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<?> findVoucherByIdAPI(@PathVariable("voucherId") String voucherId) {
        return new ResponseEntity<>(voucherService.getVoucherById(voucherId), HttpStatus.OK);
    }

    @PostMapping("vouchers")
    @ResponseBody
    public ResponseEntity<?> createCustomer(@RequestBody CreateVoucherRequest createVoucherRequest) {
        return new ResponseEntity<>(voucherService.saveVoucher(
                createVoucherRequest.getOwnerId(),
                createVoucherRequest.getType(),
                createVoucherRequest.getAmount(),
                createVoucherRequest.getExpiredAt()), HttpStatus.OK);
    }

    @DeleteMapping("vouchers/{voucherId}")
    public ResponseEntity<?> deleteVoucherByIdAPI(@PathVariable("voucherId") String voucherId) {
        voucherService.deleteVoucher(voucherId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
