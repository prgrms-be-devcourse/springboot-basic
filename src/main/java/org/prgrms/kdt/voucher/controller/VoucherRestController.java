package org.prgrms.kdt.voucher.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping("/api/v1/vouchers")
    public ResponseEntity<List<VoucherDto>> voucherList() {
        return ResponseEntity.ok(voucherService.getAllVouchers());
    }

    @GetMapping("/api/v1/vouchers/search")
    public ResponseEntity<List<VoucherDto>> voucherFilterList(VoucherSearch voucherSearch) {
        return ResponseEntity.ok(voucherService.getFilteredVouchers(voucherSearch));
    }

    @PostMapping("/api/v1/vouchers/new")
    public ResponseEntity<Object> addNewVoucher(@RequestBody @Valid VoucherDto voucherDto) {
        voucherService.createVoucher(voucherDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    public ResponseEntity<VoucherDto> voucherDetail(@PathVariable("voucherId") UUID voucherId) {
        return ResponseEntity.ok(voucherService.getVoucher(voucherId));
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public ResponseEntity<Object> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return ResponseEntity.noContent().build();
    }
}
