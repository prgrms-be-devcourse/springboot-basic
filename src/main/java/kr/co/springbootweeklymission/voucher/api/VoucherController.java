package kr.co.springbootweeklymission.voucher.api;

import kr.co.springbootweeklymission.voucher.api.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.voucher.application.VoucherService;
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
