package kr.co.springbootweeklymission.voucher.presentation;

import kr.co.springbootweeklymission.common.response.ResponseFormat;
import kr.co.springbootweeklymission.common.response.ResponseStatus;
import kr.co.springbootweeklymission.voucher.application.VoucherService;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    @PostMapping
    public ResponseFormat<Void> createVoucher(@RequestBody @Validated VoucherReqDTO.CREATE create) {
        voucherService.createVoucher(create);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    @GetMapping("/{voucher_id}")
    public ResponseFormat<VoucherResDTO.READ> getVoucherById(@PathVariable(name = "voucher_id") UUID voucherId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, voucherService.getVoucherById(voucherId));
    }
}
