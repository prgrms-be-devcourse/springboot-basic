package team.marco.voucher_management_system.web_app.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.service.VoucherService;
import team.marco.voucher_management_system.type_enum.VoucherType;
import team.marco.voucher_management_system.web_app.dto.CreateVoucherRequest;

@RestController
@RequestMapping(
        value = "/api/vouchers",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class VoucherController {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> findAll() {
        return voucherService.getVouchers();
    }

    @PostMapping
    public ResponseEntity<Voucher> create(@Valid CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = voucherService.create(createVoucherRequest);

        return ResponseEntity.ok(voucher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> findById(@PathVariable UUID id) {
        Optional<Voucher> optionalVoucher = voucherService.findById(id);

        return optionalVoucher.map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent()
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        voucherService.deleteById(id);

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/createdAt")
    public List<Voucher> findByCreateAt(@RequestParam
                                        @DateTimeFormat(pattern = DATE_PATTERN)
                                        LocalDateTime from,
                                        @RequestParam
                                        @DateTimeFormat(pattern = DATE_PATTERN)
                                        LocalDateTime to) {
        return voucherService.findByCreateAt(from, to);
    }

    @GetMapping("/type/{type}")
    public List<Voucher> findByType(@PathVariable VoucherType type) {
        return voucherService.findByType(type);
    }
}
