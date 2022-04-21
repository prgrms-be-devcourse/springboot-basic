package org.prgrms.voucher.controller;

import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.prgrms.voucher.response.ResponseState;
import org.prgrms.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {

        this.voucherService = voucherService;
    }

    public VoucherDto.CreateVoucherResponse create(VoucherDto.CreateVoucherRequest requestDto) {

        try {
            Voucher voucher = voucherService.create(requestDto);

            return new VoucherDto.CreateVoucherResponse(voucher.getVoucherId(),
                    voucher.getDiscountValue(), voucher.getVoucherType());
        } catch (NullPointerException nullPointerException) {
            logger.info("bad Request...");

            return new VoucherDto.CreateVoucherResponse(-1L, -1,
                    VoucherType.EXCEPTION, ResponseState.BAD_REQUEST);
        }
    }
}
