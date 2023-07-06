package kr.co.springbootweeklymission.voucher.presentation;

import kr.co.springbootweeklymission.common.response.ResponseFormat;
import kr.co.springbootweeklymission.common.response.ResponseStatus;
import kr.co.springbootweeklymission.voucher.application.VoucherService;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
