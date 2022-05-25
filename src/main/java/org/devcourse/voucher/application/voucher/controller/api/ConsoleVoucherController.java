package org.devcourse.voucher.application.voucher.controller.api;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Profile("console")
public class ConsoleVoucherController implements VoucherController{
    private final VoucherService voucherService;
    private final Logger logger = LoggerFactory.getLogger(ConsoleVoucherController.class);

    public ConsoleVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public Voucher postCreateVoucher(VoucherRequest voucherRequest) {
        logger.info("Controller : Processing voucher generation");
        return voucherService.createVoucher(
                voucherRequest.getVoucherType(),
                voucherRequest.getPrice()
        );
    }

    @Override
    public List<Voucher> getVoucherList() {
        logger.info("Controller : List inquire processing");
        return voucherService.recallAllVoucher();
    }
}

