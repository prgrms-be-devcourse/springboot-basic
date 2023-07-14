package com.programmers.springbasic.domain.voucher.controller;

import com.programmers.springbasic.domain.voucher.dto.request.VoucherCreateRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.request.VoucherUpdateRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.response.VoucherResponseDTO;
import com.programmers.springbasic.domain.voucher.service.VoucherService;
import com.programmers.springbasic.domain.voucher.validator.VoucherCodeValidator;
import com.programmers.springbasic.global.common.response.model.CommonResult;
import com.programmers.springbasic.global.common.response.model.ListResult;
import com.programmers.springbasic.global.common.response.model.SingleResult;
import com.programmers.springbasic.global.common.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherAPIController {
    private final VoucherService voucherService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<CommonResult> createVoucher(
            @RequestBody VoucherCreateRequestDTO voucherCreateRequestDTO) {

        voucherService.createVoucher(voucherCreateRequestDTO);

        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @GetMapping("/list/{voucherType}")
    public ResponseEntity<ListResult<VoucherResponseDTO>> getListVoucherInfo(
            @PathVariable String voucherType) {
        List<VoucherResponseDTO> voucherInfos = voucherService.getAllVoucherInfo(voucherType);

        return ResponseEntity.ok(responseService.getListResult(voucherInfos));
    }

    @GetMapping("/{voucherCode}")
    public ResponseEntity<SingleResult<VoucherResponseDTO>> getSingleVoucherInfo(
            @PathVariable String voucherCode) {
        VoucherCodeValidator.validateVoucherCode(voucherCode);

        VoucherResponseDTO voucherInfo = voucherService.findVoucher(voucherCode);

        return ResponseEntity.ok(responseService.getSingleResult(voucherInfo));
    }

    @PutMapping("/{voucherCode}")
    public ResponseEntity<CommonResult> updateVoucher(
            @RequestBody VoucherUpdateRequestDTO voucherUpdateRequestDTO) {
        VoucherCodeValidator.validateVoucherCode(voucherUpdateRequestDTO.getVoucherCode());

        voucherService.updateVoucher(voucherUpdateRequestDTO);

        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @DeleteMapping("/{voucherCode}")
    public ResponseEntity<CommonResult> deleteVoucher(
            @PathVariable String voucherCode) {
        VoucherCodeValidator.validateVoucherCode(voucherCode);

        voucherService.removeVoucher(voucherCode);

        return ResponseEntity.ok(responseService.getSuccessResult());
    }
}
