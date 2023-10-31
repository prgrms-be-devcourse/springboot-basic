package com.zerozae.voucher.controller.console.voucher;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.voucher.VoucherCreateRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
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

    public Response createVoucher(VoucherCreateRequest voucherRequest) {
        try {
            voucherService.createVoucher(voucherRequest);
            return Response.success();
        }catch (ExceptionMessage e){
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public Response<List<VoucherResponse>> findAllVouchers() {
        List<VoucherResponse> vouchers = voucherService.findAllVouchers();
        return Response.success(vouchers);
    }

    public Response<VoucherResponse> findVoucherById(UUID voucherId) {
        try{
            VoucherResponse findVoucher = voucherService.findById(voucherId);
            return Response.success(findVoucher);
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public Response deleteById(UUID voucherId) {
        try {
            voucherService.deleteById(voucherId);
            return Response.success();
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public Response deleteAll() {
        try {
            voucherService.deleteAll();
            return Response.success();
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public Response updateVoucher(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        try {
            voucherService.update(voucherId, voucherUpdateRequest);
            return Response.success();
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }
}
