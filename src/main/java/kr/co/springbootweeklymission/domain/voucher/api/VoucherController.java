package kr.co.springbootweeklymission.domain.voucher.api;

import kr.co.springbootweeklymission.domain.voucher.application.VoucherService;
import kr.co.springbootweeklymission.domain.voucher.api.request.VoucherReqDTO;
import kr.co.springbootweeklymission.domain.voucher.api.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    public void createVoucher(VoucherReqDTO.CREATE create) {
        voucherService.createVoucher(create);
    }

    public List<VoucherResDTO.READ> getVouchersAll() {
        return voucherService.getVouchersAll();
    }
}
