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

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }

    @PostMapping("/new")
    public Voucher addNewCustomer(CreateVoucherRequest createVoucherRequest) {
        Voucher saveVoucher = voucherService.save(VoucherType.of(createVoucherRequest.voucherType()), createVoucherRequest.amount());
        return saveVoucher;
    }

    @GetMapping("/list")
    public List<Voucher> showVoucherList() {
        List<Voucher> voucherList = voucherService.getVouchers();
        return voucherList;
    }

    @GetMapping("/list/{voucherType}")
    public List<Voucher> findByVoucherType(@PathVariable("voucherType") String voucherType) {
        List<Voucher> vouchersByType = voucherService.getVouchersByType(voucherType);
        return vouchersByType;
    }


    @GetMapping("/view/{voucherId}")
    public Voucher viewVoucher(@PathVariable("voucherId") Long voucherId) {
        return voucherService.getVoucherById(voucherId);
    }


    @GetMapping("/delete/{voucherId}")
    public void deleteVoucher( @PathVariable("voucherId") Long voucherId) {
        voucherService.deleteById(voucherId);
    }
}
