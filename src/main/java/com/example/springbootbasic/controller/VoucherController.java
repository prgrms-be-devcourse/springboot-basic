package com.example.springbootbasic.controller;

import com.example.springbootbasic.controller.request.RequestBody;
import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.dto.VoucherDto;
import com.example.springbootbasic.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public ResponseBody<VoucherDto> saveVoucher(RequestBody<VoucherDto> request) {
        VoucherDto voucherDto = request.getData();
        Long discountValue = voucherDto.getDiscountValue();
        VoucherType voucherType = voucherDto.getVoucherType();
        try {
            Voucher generatedVoucher = VoucherFactory.generateVoucher(discountValue, voucherType);
            Voucher savedVoucher = voucherService.saveVoucher(generatedVoucher);
            return ResponseBody.success(VoucherDto.toDto(savedVoucher));
        } catch (NullPointerException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(voucherDto);
        }
    }

    public ResponseBody<List<VoucherDto>> selectAllVouchers() {
        List<Voucher> findAllVouchers = Collections.emptyList();
        try {
            findAllVouchers = voucherService.findAllVouchers();
        } catch (NullPointerException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(Collections.emptyList());
        }
        return ResponseBody.success(findAllVouchers.stream()
                .map(VoucherDto::toDto)
                .toList());
    }
}
