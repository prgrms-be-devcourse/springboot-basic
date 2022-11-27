package com.example.springbootbasic.controller.voucher;

import com.example.springbootbasic.controller.request.RequestBody;
import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.dto.voucher.VoucherDto;
import com.example.springbootbasic.dto.voucher.VoucherTypeDto;
import com.example.springbootbasic.service.voucher.JdbcVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;

@Controller
public class JdbcVoucherController {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherController.class);
    private final JdbcVoucherService voucherService;

    public JdbcVoucherController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public ResponseBody<VoucherDto> saveVoucher(RequestBody<VoucherDto> request) {
        VoucherDto voucherDto = request.getData();
        Long discountValue = voucherDto.getVoucherDiscountValue();
        VoucherType voucherType = voucherDto.getVoucherType();
        try {
            Voucher generatedVoucher = VoucherFactory.of(discountValue, voucherType);
            Voucher savedVoucher = voucherService.saveVoucher(generatedVoucher);
            return ResponseBody.success(VoucherDto.newInstance(savedVoucher));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(voucherDto);
        }
    }

    public ResponseBody<List<VoucherDto>> selectAllVouchers() {
        List<Voucher> findAllVouchers;
        try {
            findAllVouchers = voucherService.findAllVouchers();
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(Collections.emptyList());
        }
        return ResponseBody.success(findAllVouchers.stream()
                .map(VoucherDto::newInstance)
                .toList());
    }

    public ResponseBody<List<VoucherDto>> findAllVoucherByVoucherType(RequestBody<VoucherTypeDto> request) {
        VoucherTypeDto voucherTypeDto = request.getData();
        List<Voucher> findAllVouchers = Collections.emptyList();
        try {
            VoucherType voucherType = voucherTypeDto.getVoucherType();
            findAllVouchers = voucherService.findAllVoucherByVoucherType(voucherType);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(Collections.emptyList());
        }
        return ResponseBody.success(findAllVouchers.stream()
                .map(VoucherDto::newInstance)
                .toList());
    }

    public ResponseBody<VoucherDto> updateVoucher(RequestBody<VoucherDto> request) {
        VoucherDto voucherDto = request.getData();
        Voucher updatedVoucher;
        try {
            Voucher toBeVoucher = VoucherFactory.of(voucherDto.getVoucherId(), voucherDto.getVoucherDiscountValue(), voucherDto.getVoucherType());
            updatedVoucher = voucherService.update(toBeVoucher);
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(voucherDto);
        }
        return ResponseBody.success(VoucherDto.newInstance(updatedVoucher));
    }

    public ResponseBody<VoucherDto> findVoucherById(RequestBody<Long> request) {
        Voucher findVoucher;
        try {
            findVoucher = voucherService.findById(request.getData());
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            Voucher emptyVoucher = VoucherFactory.of(request.getData(), 0L, FIXED_AMOUNT);
            return ResponseBody.fail(VoucherDto.newInstance(emptyVoucher));
        }
        return ResponseBody.success(VoucherDto.newInstance(findVoucher));
    }
}
