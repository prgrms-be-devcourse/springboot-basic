package com.prgrms.vouchermanagement.voucher.controller;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<VoucherDto> findVouchers(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {

        // Type 조회
        if (checkFindByType(type)) {
            return getVoucherListByType(type);
        }

        // 기간 조회
        if (checkFindByPeriod(from, end)) {
            return getVoucherListByPeriod(from, end);
        }

        // 전체 조회
        return getAllVoucherList();
    }

    @PostMapping
    public UUID addVoucher(@RequestBody VoucherDto voucherDto) {
        UUID voucherId = voucherService.addVoucher(voucherDto.getVoucherType(), voucherDto.getAmount());
        return voucherId;
    }

    @DeleteMapping("/{voucherId}")
    public void removeVoucher(@PathVariable UUID voucherId) {
        voucherService.removeVoucher(voucherId);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherDto> findById(@PathVariable UUID voucherId) {
        Optional<Voucher> optionalVoucher = voucherService.findVoucherById(voucherId);

        if (optionalVoucher.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        VoucherDto voucherDto = VoucherDto.from(optionalVoucher.get());
        return new ResponseEntity<>(voucherDto, HttpStatus.OK);
    }

    private boolean checkFindByPeriod(LocalDate from, LocalDate end) {
        return from != null && end != null;
    }

    private boolean checkFindByType(Integer type) {
        return type != null;
    }

    private List<VoucherDto> getVoucherListByType(Integer type) {
        List<Voucher> vouchers = voucherService.findVoucherByType(VoucherType.getVoucherType(type));
        return VoucherDto.fromList(vouchers);
    }

    private List<VoucherDto> getAllVoucherList() {
        List<Voucher> vouchers = voucherService.findAllVouchers();
        return VoucherDto.fromList(vouchers);
    }

    private List<VoucherDto> getVoucherListByPeriod(LocalDate from, LocalDate end) {
        LocalDateTime fromLocalDateTime = LocalDateTime.of(from, LocalTime.of(0, 0));
        LocalDateTime endLocalDateTime = LocalDateTime.of(end, LocalTime.of(23, 59));
        List<Voucher> vouchers = voucherService.findVoucherByPeriod(fromLocalDateTime, endLocalDateTime);
        return VoucherDto.fromList(vouchers);
    }
}
