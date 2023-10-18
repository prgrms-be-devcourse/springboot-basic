package com.zerozae.voucher.controller.voucher;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class VoucherController {

    private static final long ZERO = 0;

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response createVoucher(VoucherRequest voucherRequest){
        try {
            voucherService.createVoucher(voucherRequest);
            return Response.success();
        }catch (ErrorMessage e){
            return Response.failure(e.getMessage());
        }
    }

    public Response findAllVouchers(){
        List<VoucherResponse> vouchers = voucherService.findAllVouchers();
        return Response.success(vouchers);
    }
}
