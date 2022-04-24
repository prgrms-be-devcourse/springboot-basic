package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.Response;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequestDto;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.VoucherList;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController{
    private VoucherService voucherService;

    public Response<Voucher> create(VoucherRequestDto voucherRequestDto) {
        return null;
    }
    public Response<VoucherList> findAll() {
        return null;
    }
}
