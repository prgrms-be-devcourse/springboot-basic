package org.programmers.kdt.weekly.voucher.controller.restController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.voucher.controller.CreateVoucherRequest;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestVoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/api/v1/new-voucher")
    public Voucher create(@RequestBody CreateVoucherRequest createVoucherRequest) {

        return this.voucherService.create(
            createVoucherRequest.getVoucherType(),
            createVoucherRequest.getValue());
    }

    @GetMapping("/api/v1/vouchers")
    public List<Voucher> list() {

        return this.voucherService.getVouchers();
    }

    @GetMapping("/api/v1/vouchers/{type}")
    public List<Voucher> voucherTypeList(@PathVariable VoucherType type) {

        return this.voucherService.findByType(type);
    }

    @GetMapping(value = "/api/v1/vouchers/period/{begin}/{end}")
    public List<Voucher> getVoucherByCreatedTime(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        return this.voucherService.getVoucherByCreatedAt(begin, end);
    }

    @DeleteMapping(value = "/api/v1/vouchers/delete/{id}")
    public ResponseEntity<UUID> delete(@PathVariable UUID id) {
        var isRemoved = this.voucherService.deleteById(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}