package org.prgrms.kdt.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.request.voucher.CreateVoucherRequest;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.prgrms.kdt.utils.VoucherType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class RestApiVoucherController {

    private final VoucherService voucherService;

    @GetMapping("voucher-types")
    public List<VoucherType> voucherTypes() {
        return List.of(VoucherType.values());
    }

    @PostMapping
    public Voucher addNewVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        Voucher addNewVoucher = voucherService.save(VoucherType.of(createVoucherRequest.voucherType()), createVoucherRequest.amount());
        return addNewVoucher;
    }

    @GetMapping
    public List<Voucher> showVoucherList() {
        return voucherService.getVouchers();
    }

    @GetMapping("/type")
    public List<Voucher> findByVoucherType(@RequestParam("voucherType") String voucherType) {
        List<Voucher> vouchersByType = voucherService.getVouchersByType(voucherType);
        return vouchersByType;
    } //request-param vs pathvariable


    @GetMapping("/detail")
    public Voucher viewVoucher(@RequestParam("voucherId") Long voucherId) {
        return voucherService.getVoucherById(voucherId);
    }


    @DeleteMapping
    public void deleteVoucher(@RequestParam("voucherId") Long voucherId) {
        voucherService.deleteById(voucherId);
    }
}
