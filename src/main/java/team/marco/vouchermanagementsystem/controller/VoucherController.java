package team.marco.vouchermanagementsystem.controller;

import org.springframework.stereotype.Controller;
import team.marco.vouchermanagementsystem.service.VoucherService;

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

    public void assignVoucherOwner(UUID voucherId, UUID customerId) {
        voucherService.assignVoucherOwner(voucherId, customerId);
    }
}
