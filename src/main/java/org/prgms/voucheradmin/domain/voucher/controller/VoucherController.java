package org.prgms.voucheradmin.domain.voucher.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.dto.VoucherCondition;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherReqDto;
import org.prgms.voucheradmin.domain.voucher.dto.VouchersResDto;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.prgms.voucheradmin.global.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addVoucher(@RequestBody VoucherReqDto voucherReqDto) throws IOException {
        Voucher voucher = voucherService.createVoucher(voucherReqDto);

        ResponseDto responseDto = new ResponseDto(HttpStatus.CREATED.value(), "create voucher successfully", voucher);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getVouchers(@ModelAttribute VoucherCondition voucherCondition) throws IOException {
        List<Voucher> allVouchers = (voucherCondition.hasVoucherType() || voucherCondition.hasVoucherDateRage()) ?
                voucherService.getVouchersWithCondition(voucherCondition) : voucherService.getVouchers();

        VouchersResDto vouchersResDto = new VouchersResDto(allVouchers.size(), allVouchers);

        ResponseDto<VouchersResDto> responseDto = new ResponseDto(HttpStatus.OK.value(), "get vouchers successfully", vouchersResDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<ResponseDto> getVouchers(@PathVariable UUID voucherId) throws IOException {
        Voucher voucher = voucherService.getVoucher(voucherId);

        ResponseDto<VouchersResDto> responseDto = new ResponseDto(HttpStatus.OK.value(), "get voucher successfully", voucher);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<ResponseDto> deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);

        ResponseDto<VouchersResDto> responseDto = new ResponseDto(HttpStatus.OK.value(),
                "get voucher successfully",
                String.format("delete voucher(%s) successfully", voucherId.toString()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
