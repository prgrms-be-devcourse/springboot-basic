package org.prgrms.prgrmsspring.controller.web;

import org.prgrms.prgrmsspring.domain.VoucherType;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VoucherAPIController {
    private final VoucherService voucherService;

    public VoucherAPIController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/add")
    public Voucher create(@RequestParam Long amount, @RequestParam String type) {
        VoucherType voucherType = VoucherType.from(type);
        Voucher voucher = voucherType.constructVoucher(UUID.randomUUID(), amount);
        return voucherService.create(voucher);
    }

    public Voucher update(Voucher updateVoucher) {
        return voucherService.update(updateVoucher);
    }

    public void delete(UUID deleteVoucherId) {
        voucherService.delete(deleteVoucherId);
    }

    @PostMapping("/list/delete")
    public void deleteVouchers(@RequestParam(value = "selectedVoucherIds") List<UUID> voucherIdList) {
        voucherIdList.forEach(this::delete);
    }

    @GetMapping("/list/all")
    public List<Voucher> findAll() {
        return voucherService.findAll();
    }

    @GetMapping("/voucher")
    public Voucher findByIdFromRequest(@RequestParam UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    @PostMapping("/list/search/date")
    public List<Voucher> findBetweenDate(@RequestParam LocalDateTime begin, @RequestParam LocalDateTime end) {
        return voucherService.findBetweenDate(begin, end);
    }

    @PostMapping("/list/search/type")
    public List<Voucher> findByVoucherType(@RequestParam String discountType) {
        VoucherType voucherType = VoucherType.from(discountType);
        return voucherService.findByVoucherType(voucherType);
    }

    @GetMapping("/list/search/id")
    public List<UUID> listAllIdList() {
        return voucherService.findAll().stream().map(Voucher::getVoucherId).toList();
    }
}
