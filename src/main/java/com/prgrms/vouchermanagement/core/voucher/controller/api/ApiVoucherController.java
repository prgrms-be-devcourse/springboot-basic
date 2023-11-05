package com.prgrms.vouchermanagement.core.voucher.controller.api;

import com.prgrms.vouchermanagement.core.voucher.controller.request.VoucherCreationRequest;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherCreationResponse;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherResponse;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;
import com.prgrms.vouchermanagement.core.voucher.dto.VoucherDto;
import com.prgrms.vouchermanagement.core.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.prgrms.vouchermanagement.core.voucher.controller.Mapper.*;


@Profile("prod")
@RestController
@RequestMapping("/api/vouchers")
public class ApiVoucherController {

    private final VoucherService voucherService;

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * 바우처 전체 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<VouchersResponse> getAllVouchers() {
        List<VoucherDto> voucherDtoList = voucherService.findAll();
        return ResponseEntity.ok(toVouchersResponse(voucherDtoList));
    }

    /**
     * 바우처 추가
     *
     * @param voucherCreationRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<VoucherCreationResponse> create(@RequestBody VoucherCreationRequest voucherCreationRequest) {
        VoucherDto voucherDto = voucherService.create(toVoucherDto(voucherCreationRequest));
        return ResponseEntity.ok(toVoucherCreationResponse(voucherDto));
    }

    /**
     * 바우처 단일 조회
     *
     * @param voucherId
     * @return
     */
    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> getVoucher(@PathVariable String voucherId) {
        VoucherDto voucherDto = voucherService.getVoucher(voucherId);
        return ResponseEntity.ok(toVoucherResponse(voucherDto));
    }

}
