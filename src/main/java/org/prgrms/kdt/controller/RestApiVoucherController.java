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

    @GetMapping("voucherTypes")
    public List<VoucherType> voucherTypes() {
        return List.of(VoucherType.values());
    }

    @PostMapping("/new")
    public Voucher addNewVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        Voucher addNewVoucher = voucherService.save(VoucherType.of(createVoucherRequest.voucherType()), createVoucherRequest.amount());
        return addNewVoucher;
    }

    @GetMapping
    public List<Voucher> showVoucherList() {
        return voucherService.getVouchers();
    }

    @GetMapping("/list/{voucherType}")
    public List<Voucher> findByVoucherType(@PathVariable("voucherType") String voucherType) {
        List<Voucher> vouchersByType = voucherService.getVouchersByType(voucherType);
        return vouchersByType;
    }


    @GetMapping("/{voucherId}/detail")
    public Voucher viewVoucher(@PathVariable("voucherId") Long voucherId) {
        return voucherService.getVoucherById(voucherId);
    }


    @DeleteMapping("/delete/{voucherId}")
    public void deleteVoucher( @PathVariable("voucherId") Long voucherId) {
        voucherService.deleteById(voucherId);
    }
}
