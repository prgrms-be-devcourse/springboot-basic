package org.prgrms.kdt.domain.voucher.controller;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.request.VoucherCreateRequest;
import org.prgrms.kdt.domain.voucher.request.VoucherUpdateRequest;
import org.prgrms.kdt.domain.voucher.response.VoucherResponse;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> voucherList() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        List<VoucherResponse> voucherResponseList = vouchers.stream()
                .map(VoucherResponse::new).toList();
        return ResponseEntity.ok()
                .body(voucherResponseList);
    }

    @GetMapping("/search")
    public ResponseEntity<List<VoucherResponse>> VoucherSearch(
            @RequestParam("voucherType") VoucherType voucherType,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdDate) {
        List<Voucher> vouchers = voucherService.getVoucherByTypeAndDate(voucherType, createdDate);
        List<VoucherResponse> voucherResponseList = vouchers.stream()
                .map(VoucherResponse::new).toList();
        return ResponseEntity.ok()
                .body(voucherResponseList);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> voucherCreate(@Valid @RequestBody VoucherCreateRequest createRequest) {
        Voucher voucher = createRequest.toEntity();
        voucherService.save(voucher);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Result<Voucher>> voucherDetails(@PathVariable("voucherId") UUID voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucherById(voucherId);
        return voucher.map(value -> ResponseEntity.ok().body(new Result<>(value, 1)))
                .orElseGet(() -> ResponseEntity.ok().body(new Result<>(null, 0)));
    }

    @PutMapping("/{voucherId}")
    public ResponseEntity<HttpStatus> voucherModify(
            @Valid @RequestBody VoucherUpdateRequest updateRequest,
            @PathVariable("voucherId") UUID voucherId) {
        voucherService.update(voucherId,
                updateRequest.getVoucherType(),
                updateRequest.getDiscountValue());
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<HttpStatus> voucherRemove(@PathVariable("voucherId") UUID voucherId) {
        voucherService.remove(voucherId);
        return new ResponseEntity<>(OK);
    }

    static class Result<T> {
        private T data;
        private int count;

        public Result(T data, int count) {
            this.data = data;
            this.count = count;
        }

        public T getData() {
            return data;
        }

        public int getCount() {
            return count;
        }
    }
}
