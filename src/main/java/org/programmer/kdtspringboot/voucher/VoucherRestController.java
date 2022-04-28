package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherRestController.class);

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
                        voucherDto.getDiscountPercent()
                )
        );
    }

    @DeleteMapping("/{voucherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

    @GetMapping("/{type}")
    public List<VoucherDto> findByType(@PathVariable("type") String type) {
        return null;
    }

    @GetMapping("/")
    public List<VoucherDto> findByCreatedAt(
            @RequestParam(value = "from", required = false) LocalDateTime from,
            @RequestParam(value = "to", required = false) LocalDateTime to
    ) {
        return null;
    }
}
