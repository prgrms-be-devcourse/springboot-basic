package org.programmer.kdtspringboot.voucher;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> findAll() {
        return voucherService.findAllVouchers();
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> findById(@PathVariable("voucherId") UUID voucherId) {
        Optional<Voucher> voucher = voucherService.findByIdVoucher(voucherId);
        return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public VoucherDto create(@RequestBody VoucherDto voucherDto) {
        return new VoucherDto(
                voucherService.createVoucher(
                        voucherDto.getType(),
                        voucherDto.getDiscountAmount()
                )
        );
    }

    @DeleteMapping("/{voucherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

    @GetMapping("/type/{type}")
    public List<Voucher> findByType(@PathVariable("type") String type) {
        return voucherService.findByType(type);
    }

    @GetMapping("/time")
    public List<Voucher> findByCreatedAt(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "date", required = false) LocalDate createdAt
    ) {
        return voucherService.findByCreatedAt(createdAt);
    }
}
