package com.example.voucher_manager.domain.voucher;

import com.example.voucher_manager.web.Util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherViewController.class);

    private final Status RESPONSE_SUCCESS = Status.SUCCESS;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // ===== API =====
    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public ResponseEntity<List<VoucherDto>> findVouchers(@RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
            List<VoucherDto> vouchers = voucherService.findAll().stream()
                    .map(VoucherDto::from)
                    .toList();
            return ResponseEntity.ok(vouchers);
        }

        var vouchers = findByQueryParams(params).stream()
                .map(VoucherDto::from)
                .toList();
        return ResponseEntity.ok(vouchers);
    }

    private List<Voucher> findByQueryParams(Map<String, String> params) {
        if (params.containsKey(VoucherRequestParams.TYPE.getKey())){
            return voucherService.findSameTypeVoucher(VoucherType.of(params.get(VoucherRequestParams.TYPE.getKey())));
        }

        return voucherService.findVouchersByPeriods(
                LocalDateTime.parse(params.get(VoucherRequestParams.START.getKey()), formatter),
                LocalDateTime.parse(params.get(VoucherRequestParams.END.getKey()), formatter));
    }

    @GetMapping("/api/v1/voucher/{voucherId}")
    @ResponseBody
    public ResponseEntity<VoucherDto> findVoucher(@PathVariable("voucherId") UUID voucherId) {
        var voucher = voucherService.findVoucher(voucherId);
        if (voucher.isPresent()) {
            var voucherDto = VoucherDto.from(voucher.get());
            return ResponseEntity.ok(voucherDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/v1/voucher")
    @ResponseBody
    public ResponseEntity<VoucherDto> createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        var voucher = voucherService.createVoucher(
                VoucherType.of(createVoucherRequest.voucherType()),
                createVoucherRequest.discountInfo());

        return ResponseEntity.ok(VoucherDto.from(voucher));
    }

    @DeleteMapping("/api/v1/voucher/{voucherId}")
    @ResponseBody
    public ResponseEntity<Status> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        var response = voucherService.deleteVoucher(voucherId);
        return response ? ResponseEntity.ok(RESPONSE_SUCCESS) : ResponseEntity.internalServerError().build();
    }
}
