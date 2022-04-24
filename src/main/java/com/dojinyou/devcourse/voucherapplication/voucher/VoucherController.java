package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.Response;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequestDto;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController{
    private VoucherService voucherService;

    public Response create(VoucherRequestDto voucherRequestDto) {
        return null;
    }
    public Response findAll() {
        return null;
    }
}
