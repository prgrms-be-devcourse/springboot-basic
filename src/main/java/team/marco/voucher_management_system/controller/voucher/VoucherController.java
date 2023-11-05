package team.marco.voucher_management_system.controller.voucher;

import org.springframework.stereotype.Controller;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherCreateRequest;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherResponse;
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

    public VoucherResponse getVoucher(String voucherId) {
        return VoucherResponse.of(voucherService.getVoucher(Long.valueOf(voucherId)));
    }

    public List<VoucherResponse> getVouchers() {
        return voucherService.getVouchers().stream()
                .map(VoucherResponse::of)
                .toList();
    }

    public void deleteVoucher(String voucherId) {
        voucherService.deleteVoucher(Long.valueOf(voucherId));
    }
}
