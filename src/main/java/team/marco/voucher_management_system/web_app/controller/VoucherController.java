package team.marco.voucher_management_system.web_app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.service.VoucherService;
import team.marco.voucher_management_system.type_enum.VoucherType;
import team.marco.voucher_management_system.web_app.dto.CreateVoucherRequest;

@Controller
public class VoucherController {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    @ResponseBody
    public List<Voucher> findAll() {
        return voucherService.getVouchers();
    }

    @PostMapping("/vouchers")
    @ResponseBody
    public ResponseEntity<Voucher> create(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = voucherService.create(createVoucherRequest);

        return ResponseEntity.ok(voucher);
    }

    @GetMapping("/vouchers/{id}")
    @ResponseBody
    public ResponseEntity<Voucher> findById(@PathVariable("id") UUID id) {
        Optional<Voucher> optionalVoucher = voucherService.findById(id);

        return optionalVoucher.map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent()
                        .build());
    }

    @DeleteMapping("/vouchers/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        boolean isDeleted = voucherService.deleteById(id);

        if (!isDeleted) {
            return ResponseEntity.noContent()
                    .build();
        }

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/vouchers/createdAt")
    @ResponseBody
    public List<Voucher> findByCreateAt(@RequestParam("from")
                                        @DateTimeFormat(pattern = DATE_PATTERN)
                                        LocalDateTime from,
                                        @RequestParam("to")
                                        @DateTimeFormat(pattern = DATE_PATTERN)
                                        LocalDateTime to) {
        return voucherService.findByCreateAt(from, to);
    }

    @GetMapping("/vouchers/type/{type}")
    @ResponseBody
    public List<Voucher> findByType(@PathVariable("type") VoucherType voucherType) {
        return voucherService.findByType(voucherType);
    }
}
