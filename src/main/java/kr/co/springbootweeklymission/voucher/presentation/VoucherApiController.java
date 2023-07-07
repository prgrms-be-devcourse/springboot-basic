package kr.co.springbootweeklymission.voucher.presentation;

import kr.co.springbootweeklymission.common.response.ResponseStatus;
import kr.co.springbootweeklymission.voucher.application.VoucherService;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<String> createVoucher(@RequestBody @Validated VoucherReqDTO.CREATE create) {
        voucherService.createVoucher(create);
        return ResponseEntity
                .status(ResponseStatus.SUCCESS_CREATE_VOUCHER.getHttpStatus())
                .body(ResponseStatus.SUCCESS_CREATE_VOUCHER.getMessage());
    }

    @GetMapping("/{voucher_id}")
    public ResponseEntity<VoucherResDTO.READ> getVoucherById(@PathVariable(name = "voucher_id") UUID voucherId) {
        return ResponseEntity
                .ok()
                .body(voucherService.getVoucherById(voucherId));
    }
}
