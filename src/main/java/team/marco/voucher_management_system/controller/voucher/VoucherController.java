package team.marco.voucher_management_system.controller.voucher;

import org.springframework.stereotype.Controller;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createVoucher(VoucherCreateRequest request) {
        voucherService.createVoucher(request.toServiceRequest());
    }

    public String getVoucherInfo(String voucherId) {
        return voucherService.getVoucher(Long.valueOf(voucherId)).toString();
    }

    public List<String> getVouchersInfo() {
        return voucherService.getVouchers().stream()
                .map(Object::toString)
                .toList();
    }

    public void deleteVoucher(String voucherId) {
        voucherService.deleteVoucher(Long.valueOf(voucherId));
    }
}
