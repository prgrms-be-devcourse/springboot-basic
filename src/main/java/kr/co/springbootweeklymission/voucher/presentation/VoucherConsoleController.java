package kr.co.springbootweeklymission.voucher.presentation;

import kr.co.springbootweeklymission.voucher.application.VoucherService;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherConsoleController {
    private final VoucherService voucherService;

    public void createVoucher(VoucherReqDTO.CREATE create) {
        voucherService.createVoucher(create);
    }

    public VoucherResDTO.READ getVoucherById(UUID voucherId) {
        return voucherService.getVoucherById(voucherId);
    }

    public List<VoucherResDTO.READ> getVouchersAll() {
        return voucherService.getVouchersAll();
    }

    public void updateVoucherById(UUID voucherId,
                                  VoucherReqDTO.UPDATE update) {
        voucherService.updateVoucherById(voucherId, update);
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
    }
}
