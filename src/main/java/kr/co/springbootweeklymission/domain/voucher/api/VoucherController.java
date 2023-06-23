package kr.co.springbootweeklymission.domain.voucher.api;

import kr.co.springbootweeklymission.domain.voucher.application.VoucherService;
import kr.co.springbootweeklymission.domain.voucher.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.domain.voucher.dto.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

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
