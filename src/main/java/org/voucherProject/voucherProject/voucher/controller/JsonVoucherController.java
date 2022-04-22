package org.voucherProject.voucherProject.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherDto;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import org.voucherProject.voucherProject.voucher.service.VoucherService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class JsonVoucherController {

    private final VoucherService voucherService;

    @PostMapping("/v1/voucher/new")
    public void createVoucher(@RequestBody VoucherDto voucherDto) {
        Voucher voucher = voucherDto.getVoucherType().createVoucher(
                voucherDto.getAmount(),
                voucherDto.getCustomerId());
        voucherService.save(voucher);
    }

    @GetMapping("/v1/voucher/{id}")
    public VoucherDto findById(@PathVariable("id") UUID voucherId) {
        return VoucherDto.of(voucherService.findById(voucherId));
    }

    @GetMapping("/v1/vouchers")
    public List<VoucherDto> findAll() {
        return voucherService.findAll()
                .stream()
                .map(VoucherDto::of).toList();
    }

    @PutMapping("/v1/voucher/order")
    public void useVoucher(@RequestBody VoucherDto voucherDto) {
        Voucher voucher = voucherService.findById(voucherDto.getVoucherId());
        voucher.useVoucher();
        voucherService.updateVoucher(voucher);
    }


    @PutMapping("/v1/voucher/cancel")
    public void cancelVoucher(@RequestBody VoucherDto voucherDto) {
        Voucher voucher = voucherService.findById(voucherDto.getVoucherId());
        voucher.cancelVoucher();
        voucherService.updateVoucher(voucher);
    }

    @DeleteMapping("/v1/voucher/delete")
    public void deleteVoucher(@RequestBody VoucherDto voucherDto) {
        UUID voucherId = voucherDto.getVoucherId();
        UUID customerId = voucherDto.getCustomerId();
        voucherService.deleteOneVoucherByCustomer(customerId, voucherId);
    }

    @GetMapping("/v1/vouchers/type")
    public List<VoucherDto> findByVoucherType(@RequestParam("type") String voucherType) {
        List<Voucher> byVoucherType = voucherService.findByVoucherType(VoucherType.valueOf(voucherType.toUpperCase()));
        return byVoucherType.stream().map(VoucherDto::of).toList();
    }

    @GetMapping("/v1/voucher/created-at")
    public List<VoucherDto> findByCreatedAtBetweenAandB(@RequestParam("date1") String date1,
                                                        @RequestParam("date2") String date2) {
        List<Voucher> byCreatedAtBetween = voucherService.findByCreatedAtBetween(date1, date2);
        return byCreatedAtBetween.stream().map(VoucherDto::of).toList();
    }
}
