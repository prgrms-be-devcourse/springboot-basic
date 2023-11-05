package com.prgrms.vouchermanagement.core.voucher.controller.api;

import com.prgrms.vouchermanagement.core.voucher.controller.request.VoucherCreateRequest;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherCreateResponse;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VoucherDeleteResponse;
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
     * @param voucherCreateRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<VoucherCreateResponse> create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        VoucherDto voucherDto = voucherService.create(toVoucherDto(voucherCreateRequest));
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

    /**
     * 바우처 삭제
     *
     * @param voucherId
     * @return
     */
    @DeleteMapping("/{voucherId}")
    public ResponseEntity<VoucherDeleteResponse> deleteVoucher(@PathVariable String voucherId) {
        String id = voucherService.deleteById(voucherId);
        return ResponseEntity.ok(toVoucherDeleteResponse(id));
    }

}
