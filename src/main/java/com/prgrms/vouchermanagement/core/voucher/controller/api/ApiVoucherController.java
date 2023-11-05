package com.prgrms.vouchermanagement.core.voucher.controller.api;

import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;
import com.prgrms.vouchermanagement.core.voucher.dto.VoucherDto;
import com.prgrms.vouchermanagement.core.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.prgrms.vouchermanagement.core.voucher.controller.Mapper.toVouchersResponse;

@Profile("prod")
@RestController
@RequestMapping("/api/vouchers")
public class ApiVoucherController {

    private final VoucherService voucherService;

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * 전체 바우처 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<VouchersResponse> getAllVouchers() {
        List<VoucherDto> voucherDtoList = voucherService.findAll();
        return ResponseEntity.ok(toVouchersResponse(voucherDtoList));
    }

}
