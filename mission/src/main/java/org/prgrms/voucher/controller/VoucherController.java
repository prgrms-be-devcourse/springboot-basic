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
            if (requestDto == null){
                throw new IllegalArgumentException("please retry.");
            }
            Voucher voucher = voucherService.create(requestDto);

            return new Response(ResponseState.SUCCESS, VoucherDto.CreateVoucherResponse.from(voucher));
        } catch (IllegalArgumentException argumentException) {
            logger.error("RequestDto : {}", requestDto, argumentException);

            return new Response(ResponseState.BAD_REQUEST, argumentException.getMessage());
        }
    }
}
