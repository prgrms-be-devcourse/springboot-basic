package com.example.voucher.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.VoucherServiceType;
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
        return switch (voucherRequest.getVoucherServiceType()) {
            case CREATE -> createVoucher(voucherRequest.getVoucherType(), voucherRequest.getDiscountValue());
            case LIST -> getVouchers();
            case REMOVE -> removeVoucher();
        };

    }

    public VoucherResponse createVoucher(Voucher.Type voucherType, long discountValue) {
        VoucherResponse voucherResponse = new VoucherResponse(VoucherServiceType.CREATE);

        VoucherDTO voucher = voucherService.createVoucher(voucherType, discountValue);
        voucherResponse.setVoucher(voucher);

        return voucherResponse;
    }

    public VoucherResponse getVouchers() {
        VoucherResponse voucherResponse = new VoucherResponse(VoucherServiceType.LIST);

        List<VoucherDTO> vouchers = voucherService.getVouchers();
        voucherResponse.setVoucherDTOS(vouchers);

        return voucherResponse;
    }

    public VoucherResponse removeVoucher() {
        voucherService.removeVouchers();

        return new VoucherResponse(VoucherServiceType.REMOVE);
    }

}
