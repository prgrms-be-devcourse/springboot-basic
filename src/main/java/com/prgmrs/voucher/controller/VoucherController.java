package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.ResponseDTO;
import com.prgmrs.voucher.dto.request.UsernameRequest;
import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.enums.StatusCode;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.service.VoucherService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public ResponseDTO<?> createVoucher(VoucherRequest voucherRequest) {
        try {
            return new ResponseDTO<>(voucherService.createVoucher(voucherRequest), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<?> findAll() {
        try {
            return new ResponseDTO<>(voucherService.findAll(), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<?> getAssignedVoucherListByUsername(UsernameRequest usernameRequest) {
        try {
            return new ResponseDTO<>(voucherService.getAssignedVoucherListByUsername(usernameRequest), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<?> getNotAssignedVoucherList() {
        try {
            return new ResponseDTO<>(voucherService.getNotAssignedVoucher(), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<?> getAssignedVoucherList() {
        try {
            return new ResponseDTO<>(voucherService.getAssignedVoucherList(), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }
}
