package com.zerozae.voucher.controller.voucher;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;

@Controller
public class VoucherController {

    private static final long ZERO = 0;

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response createVoucher(VoucherRequest voucherRequest){
        try{
            validateVoucherInfo(voucherRequest);
        }catch (ErrorMessage e){
            return Response.failure(e.getMessage());
        }
        voucherService.createVoucher(voucherRequest);
        return Response.success();
    }

    public Response findAllVouchers(){
        return Response.success(voucherService.findAllVouchers().stream().map(VoucherResponse::getInfo).toList());
    }

    private void validateVoucherInfo(VoucherRequest voucherRequest){
        if(voucherRequest.getDiscount() < ZERO) {
            throw ErrorMessage.error(getMessage("NEGATIVE_VOUCHER_DISCOUNT.MSG"));
        }
    }
}
