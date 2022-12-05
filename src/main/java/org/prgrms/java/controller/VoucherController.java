package org.prgrms.java.controller;

import org.prgrms.java.domain.voucher.CreateVoucherRequest;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.domain.voucher.VoucherDto;
import org.prgrms.java.service.VoucherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public List<Voucher> findVouchersAPI() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("/api/v1/vouchers/owner/{customerId}")
    @ResponseBody
    public List<Voucher> findVouchersByOwnerAPI(@PathVariable("customerId") UUID customerId) {
        return voucherService.getVoucherByOwner(customerId);
    }

    @GetMapping("/api/v1/vouchers/expired")
    @ResponseBody
    public List<Voucher> findExpiredVouchersAPI() {
        return voucherService.getExpiredVouchers();
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public Voucher findVoucherByIdAPI(@PathVariable("voucherId") UUID voucherId) {
        return voucherService.getVoucher(voucherId);
    }

    @PostMapping("/api/v1/vouchers")
    @ResponseBody
    public Voucher createCustomer(@RequestBody CreateVoucherRequest createVoucherRequest) {
        return voucherService.saveVoucher(
                createVoucherRequest.getOwnerId(),
                createVoucherRequest.getType(),
                createVoucherRequest.getAmount(),
                createVoucherRequest.getExpiredAt());
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public HttpEntity<?> deleteVoucherByIdAPI(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return new HttpEntity<>(HttpStatus.OK);
    }


    @GetMapping("/voucher")
    public String viewVoucherPage(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher";
    }

    @GetMapping("/voucher/new")
    public String createVoucherPage() {
        return "new-voucher";
    }

    @GetMapping("/voucher/{voucherId}")
    public String viewVoucherDetailPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

    @PostMapping("/voucher")
    public String createVoucher(CreateVoucherRequest createVoucherRequest) {
        voucherService.saveVoucher(
                createVoucherRequest.getOwnerId(),
                createVoucherRequest.getType(),
                createVoucherRequest.getAmount(),
                createVoucherRequest.getExpiredAt());

        return "redirect:/voucher";
    }

    @PutMapping("/voucher")
    public String putVoucher(VoucherDto voucherDto) {
        voucherService.updateVoucher(voucherDto.getVoucherId(), voucherDto.getOwnerId(), voucherDto.getExpiredAt(), voucherDto.isUsed());

        return "redirect:/voucher";
    }

    @DeleteMapping("/voucher/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/voucher";
    }

    public Voucher createVoucher(long amount, String type, LocalDateTime expiredAt) {
        return voucherService.saveVoucher(null, type, amount, expiredAt);
    }

    public Voucher findVoucherById(UUID voucherId) {
        return voucherService.getVoucher(voucherId);
    }

    public List<Voucher> findVouchersByOwner(UUID customerId) {
        return voucherService.getVoucherByOwner(customerId);
    }

    public List<Voucher> findVouchers() {
        return voucherService.getAllVouchers();
    }

    public Voucher updateVoucher(UUID voucherId, UUID ownerId, LocalDateTime expiredAt, boolean used) {
        return voucherService.updateVoucher(voucherId, ownerId, expiredAt, used);
    }

    public Voucher useVoucher(UUID voucherId) {
        return voucherService.useVoucher(voucherId);
    }

    public Voucher allocateVoucher(UUID voucherId, UUID customerId) {
        return voucherService.allocateVoucher(voucherId, customerId);
    }

    public Voucher detachOwnerFromVoucher(UUID voucherId) {
        return voucherService.detachOwnerFromVoucher(voucherId);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

    public long deleteVouchers() {
        return voucherService.deleteAllVouchers();
    }
}
