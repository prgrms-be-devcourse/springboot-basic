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
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Profile("web")
@RequestMapping("/v1/voucher")
public class RestWebVoucherController {

    private final VoucherService voucherService;

    public RestWebVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherType type = request.getType();
        long amount = request.getAmount();
        VoucherResponse voucher = voucherService.createVoucher(type, amount);

        return ResponseEntity.ok().body(voucher);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> findById(@PathVariable UUID voucherId) {
        VoucherResponse response = voucherService.findById(voucherId);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<VoucherResponse>> findByType(@PathVariable Integer typeId) {
        List<VoucherResponse> response = voucherService.findByType(typeId);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/date/{createdAt}")
    public ResponseEntity<List<VoucherResponse>> findByDate(@PathVariable String createdAt) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parsedDate = inputFormat.parse(createdAt);
            String formattedDate = outputFormat.format(parsedDate);
            List<VoucherResponse> response = voucherService.findByDate(formattedDate);

            return ResponseEntity.ok().body(response);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<VoucherResponse>> findAll() {
        List<VoucherResponse> responses = voucherService.listAllVoucher();

        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);

        return ResponseEntity.noContent().build();
    }
}
