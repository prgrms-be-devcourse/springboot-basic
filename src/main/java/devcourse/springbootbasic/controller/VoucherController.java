package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.VoucherCreateRequest;
import devcourse.springbootbasic.dto.VoucherFindResponse;
import devcourse.springbootbasic.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void createVoucher(VoucherCreateRequest voucherCreateRequest) {
        this.voucherService.create(voucherCreateRequest);
    }

    public List<VoucherFindResponse> findAllVouchers() {
        return this.voucherService.findAll();
    }
}
