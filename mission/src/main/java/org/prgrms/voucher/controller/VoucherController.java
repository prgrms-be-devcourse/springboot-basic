package org.prgrms.voucher.controller;

import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.response.Response;
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

    public Response create(VoucherDto.CreateVoucherRequest requestDto) {

        try {
            Voucher voucher = voucherService.create(requestDto);

            return new Response(ResponseState.SUCCESS, VoucherDto.CreateVoucherResponse.of(voucher));
        } catch (NullPointerException nullPointerException) {
            logger.error("bad Request...");
            nullPointerException.printStackTrace();

            return new Response(ResponseState.BAD_REQUEST, "please retry.");
        }
    }
}
