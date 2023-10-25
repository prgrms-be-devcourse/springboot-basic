package com.zerozae.voucher.controller.voucher;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;


@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response createVoucher(VoucherRequest voucherRequest) {
        try {
            voucherService.createVoucher(voucherRequest);
            return Response.success();
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response findAllVouchers() {
        List<VoucherResponse> vouchers = voucherService.findAllVouchers();
        return Response.success(vouchers);
    }

    public Response findVoucherById(UUID voucherId) {
        try{
            VoucherResponse findVoucher = voucherService.findById(voucherId);
            return Response.success(findVoucher);
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response deleteById(UUID voucherId) {
        try {
            voucherService.deleteById(voucherId);
            return Response.success();
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response deleteAll() {
        try {
            voucherService.deleteAll();
            return Response.success();
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public Response updateVoucher(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        try {
            voucherService.update(voucherId, voucherUpdateRequest);
            return Response.success();
        }catch (Exception e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }
}
