package team.marco.voucher_management_system.controller.voucher;

import org.springframework.stereotype.Controller;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void createFixedAmountVoucher(int amount) {
        voucherService.createFixedAmountVoucher(amount);
    }

    public void createPercentDiscountVoucher(int percent) {
        voucherService.createPercentDiscountVoucher(percent);
    }

    public String getVoucherInfo(String voucherId) {
        return voucherService.getVoucher(UUID.fromString(voucherId)).toString();
    }

    public List<String> getVouchersInfo() {
        return voucherService.getVouchers().stream()
                .map(Object::toString)
                .toList();
    }

    public List<String> getVouchersInfo(String customerId) {
        return voucherService.getVouchers(UUID.fromString(customerId)).stream()
                .map(Object::toString)
                .toList();
    }

    public void assignVoucherOwner(String voucherId, String customerId) {
        voucherService.assignVoucherOwner(UUID.fromString(voucherId), UUID.fromString(customerId));
    }

    public void deleteVoucher(String voucherId) {
        voucherService.deleteVoucher(UUID.fromString(voucherId));
    }
}
