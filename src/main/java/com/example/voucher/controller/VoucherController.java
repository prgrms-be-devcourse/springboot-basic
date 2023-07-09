package com.example.voucher.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.service.VoucherService;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher createVoucher(VoucherType voucherType, long discountValue) {

        try {
            return voucherService.createVoucher(voucherType, discountValue);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<VoucherDTO> getVouchers() {
        return voucherService.getVouchers()
            .stream()
            .map(o -> new VoucherDTO(o.getValue(), o.getVoucherType()))
            .toList();
    }

}
