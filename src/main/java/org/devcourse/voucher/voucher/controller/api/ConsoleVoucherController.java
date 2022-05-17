package org.devcourse.voucher.voucher.controller.api;

import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.devcourse.voucher.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class ConsoleVoucherController implements VoucherController{
    private final VoucherService voucherService;
    private final Logger logger = LoggerFactory.getLogger(ConsoleVoucherController.class);

    public ConsoleVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public Voucher postCreateVoucher(VoucherType voucherType, long discount) {
        logger.info("Controller : Processing voucher generation");
        return voucherService.createVoucher(voucherType, discount);
    }

    @Override
    public List<Voucher> getVoucherList() {
        logger.info("Controller : List inquire processing");
        return voucherService.recallAllVoucher();
    }
}

