package kr.co.programmers.school.voucher.domain.voucher.controller;

import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherListResponse;
import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherRequest;
import kr.co.programmers.school.voucher.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@Controller
public class VoucherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createVoucher(VoucherRequest voucherRequest) {
        LOGGER.debug("[Voucher Controller] createVoucher : input {}", voucherRequest.toString());
        voucherService.createVoucher(voucherRequest);
    }

    public VoucherListResponse getAllVouchers() {
        LOGGER.debug("[Voucher Controller] getAllVouchers");
        return voucherService.getAllVouchers();
    }
}