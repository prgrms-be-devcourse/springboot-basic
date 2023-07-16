package kr.co.programmers.springbootbasic.voucher.controller;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherCreateRequest;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Profile("web")
@RequestMapping("/v1/vouchers")
public class RestWebVoucherController {

    private final VoucherService voucherService;

    public RestWebVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherType type = request.type();
        long amount = request.amount();
        VoucherResponse voucher = voucherService.createVoucher(type, amount);

        return ResponseEntity
                .ok()
                .body(voucher);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> findById(@PathVariable UUID voucherId) {
        VoucherResponse response = voucherService.findById(voucherId);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> findVoucher(@RequestParam(required = false)
                                                            String type,
                                                             @RequestParam(required = false)
                                                            String date) {
        if (type != null && date == null) {
            VoucherType voucherType = VoucherType.resolveType(type);
            List<VoucherResponse> response = voucherService.findByType(voucherType);

            return ResponseEntity
                    .ok()
                    .body(response);
        }
        if (type == null && date != null) {
            List<VoucherResponse> response = voucherService.findByDate(date);

            return ResponseEntity
                    .ok()
                    .body(response);
        }

        List<VoucherResponse> responses = voucherService.findAllVoucher();

        return ResponseEntity
                .ok()
                .body(responses);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
