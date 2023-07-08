package com.example.voucher.controller;

import static com.example.voucher.utils.ExceptionMessage.INVALID_ARGUMENT_CANT_CREATE;
import static com.example.voucher.utils.ExceptionMessage.EXCEPTION_CANT_CREATE;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.service.VoucherService;
import com.example.voucher.utils.ExceptionHandler;
import com.example.voucher.utils.Validator;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createVoucher(VoucherType voucherType, long discountValue) {
        try {
            voucherService.createVoucher(voucherType, discountValue);

        } catch (IllegalArgumentException e) {
            ExceptionHandler.handleException(new IllegalArgumentException(INVALID_ARGUMENT_CANT_CREATE));

        } catch (Exception e) {
            ExceptionHandler.handleException(new Exception(EXCEPTION_CANT_CREATE));

        }
    }

    public List<VoucherDTO> getVouchers() {
        List<Voucher> vouchers = voucherService.getVouchers();

        List<VoucherDTO> voucherDTOS = vouchers.stream()
            .map(o -> new VoucherDTO(o.getValue(), o.getVoucherType()))
            .collect(Collectors.toList());

        return voucherDTOS;
    }

}
