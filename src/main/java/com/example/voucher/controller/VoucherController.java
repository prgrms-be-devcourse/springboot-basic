package com.example.voucher.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.controller.response.VoucherResponse;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.service.VoucherService;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse run(VoucherRequest voucherRequest) {
        return switch (voucherRequest.getType()) {
            case CREATE -> createVoucher(voucherRequest.getVoucherType(), voucherRequest.getDiscountValue());
            case LIST -> getVouchers();
            case REMOVE -> removeVoucher();
            case SEARCH_BY_ID -> getVoucherById(voucherRequest.getVoucherId());
        };

    }

    public VoucherResponse createVoucher(Voucher.Type voucherType, long discountValue) {
        VoucherResponse voucherResponse = new VoucherResponse(VoucherResponse.Type.OBJECT);

        VoucherDTO voucher = voucherService.createVoucher(voucherType, discountValue);
        voucherResponse.setVoucher(voucher);

        return voucherResponse;
    }

    public VoucherResponse getVouchers() {
        VoucherResponse voucherResponse = new VoucherResponse(VoucherResponse.Type.LIST);

        List<VoucherDTO> vouchers = voucherService.getVouchers();
        voucherResponse.setVoucherDTOS(vouchers);

        return voucherResponse;
    }

    public VoucherResponse removeVoucher() {
        voucherService.removeVouchers();

        return new VoucherResponse(VoucherResponse.Type.NONE);
    }

    public VoucherResponse getVoucherById(UUID voucherId){
        VoucherResponse voucherResponse = new VoucherResponse(VoucherResponse.Type.OBJECT);

        VoucherDTO voucher = voucherService.getVoucherById(voucherId);
        voucherResponse.setVoucher(voucher);

        return voucherResponse;
    }

}
