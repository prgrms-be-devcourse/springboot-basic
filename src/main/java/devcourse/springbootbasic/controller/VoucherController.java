package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.VoucherCreateRequest;
import devcourse.springbootbasic.dto.VoucherCreateResponse;
import devcourse.springbootbasic.dto.VoucherFindResponse;
import devcourse.springbootbasic.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherCreateResponse createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return new VoucherCreateResponse(this.voucherService.create(voucherCreateRequest));
    }

    public List<VoucherFindResponse> findAllVouchers() {
        return this.voucherService.findAll();
    }
}
