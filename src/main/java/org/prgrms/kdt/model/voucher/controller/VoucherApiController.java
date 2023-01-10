package org.prgrms.kdt.model.voucher.controller;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.dto.AssignVoucherRequest;
import org.prgrms.kdt.model.voucher.dto.CreateVoucherRequest;
import org.prgrms.kdt.model.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public List<Voucher> voucherLists() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("/api/v1/vouchers/date")
    public List<Voucher> voucherListsByDate(
            @RequestParam(value = "startDate", defaultValue = "00010101") String startDate,
            @RequestParam(value = "endDate", defaultValue = "99991231") String endDate
    ) {
        LocalDateTime startLocalDateTime = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0, 0, 0);
        LocalDateTime endLocalDateTime = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(23, 59, 59);
        return voucherService.getAllVouchers().stream()
                .filter(voucher -> voucher.getCreatedAt().isAfter(startLocalDateTime) && voucher.getCreatedAt().isBefore(endLocalDateTime))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/vouchers/type")
    public List<Voucher> voucherListsByVoucherType(@RequestParam(value = "voucherType", defaultValue = "FixedAmountVoucher") String voucherType) {
        return voucherService.getAllVouchers().stream()
                .filter(voucher -> voucher.getVoucherType().equals(voucherType))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/vouchers/details/{voucherId}")
    public Voucher voucherDetails(@PathVariable String voucherId) {
        return voucherService.findVoucherById(voucherId);
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public UUID voucherRemove(@PathVariable String voucherId) {
        return voucherService.removeVoucher(voucherId);
    }

    @PostMapping("/api/v1/new-voucher")
    public Voucher newVoucher(@RequestBody CreateVoucherRequest request) {
        return voucherService.create(request.voucherType(), Double.toString(request.discountAmount()));
    }

    @PostMapping("/api/v1/customers/voucher_wallet")
    public List<Voucher> customerVoucherWallet(String wallet_customerId) {
        return voucherService.getOwnedVouchers(wallet_customerId);
    }

    @DeleteMapping("/api/v1/customers/voucher_wallet/{voucherId}")
    public Voucher customerOwnedVoucherRemove(@PathVariable String voucherId) {
        return voucherService.removeAssignment(voucherId);
    }

    @GetMapping("/api/v1/not-owned-lists")
    public List<Voucher> notOwnedVoucherLists() {
        return voucherService.getAllVouchers().stream()
                .filter(voucher -> voucher.getOwnedCustomerId().isEmpty())
                .collect(Collectors.toList());
    }

    @PostMapping("/api/v1/owned-voucher")
    public Voucher ownedVoucher(@RequestBody AssignVoucherRequest assignVoucherRequest) {
        Voucher findVoucher = voucherService.findVoucherById(assignVoucherRequest.voucherId());
        return voucherService.assignVoucher(findVoucher, UUID.fromString(assignVoucherRequest.customerId()));
    }
}
