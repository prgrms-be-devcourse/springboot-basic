package org.prgrms.java.controller.api;

import org.prgrms.java.domain.voucher.CreateVoucherRequest;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.service.VoucherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("vouchers")
    @ResponseBody
    public List<Voucher> findVouchersAPI() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("vouchers/owner/{customerId}")
    @ResponseBody
    public List<Voucher> findVouchersByOwnerAPI(@PathVariable("customerId") UUID customerId) {
        return voucherService.getVoucherByOwnerId(customerId);
    }

    @GetMapping("vouchers/expired")
    @ResponseBody
    public List<Voucher> findExpiredVouchersAPI() {
        return voucherService.getAllExpiredVouchers();
    }

    @GetMapping("vouchers/{voucherId}")
    @ResponseBody
    public Voucher findVoucherByIdAPI(@PathVariable("voucherId") UUID voucherId) {
        return voucherService.getVoucherById(voucherId);
    }

    @PostMapping("vouchers")
    @ResponseBody
    public Voucher createCustomer(@RequestBody CreateVoucherRequest createVoucherRequest) {
        return voucherService.saveVoucher(
                createVoucherRequest.getOwnerId(),
                createVoucherRequest.getType(),
                createVoucherRequest.getAmount(),
                createVoucherRequest.getExpiredAt());
    }

    @DeleteMapping("vouchers/{voucherId}")
    public HttpEntity<?> deleteVoucherByIdAPI(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return new HttpEntity<>(HttpStatus.OK);
    }
}
