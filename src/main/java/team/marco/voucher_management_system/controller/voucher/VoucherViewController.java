package team.marco.voucher_management_system.controller.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherResponse;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.List;

@Controller
@RequestMapping("/vouchers")
public class VoucherViewController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherViewController.class);
    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping()
    public String findAllVouchers(Model model) {
        List<VoucherResponse> vouchers = voucherService.getVouchers().stream()
                .map(VoucherResponse::of)
                .toList();

        model.addAttribute("vouchers", vouchers);

        return "voucher/voucher_list";
    }

    @GetMapping("/{voucherId}")
    public String findVoucher(@PathVariable Long voucherId, Model model) {
        Voucher voucher = voucherService.getVoucher(voucherId);

        model.addAttribute("voucher", voucher);

        return "voucher/voucher_detail";
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable Long voucherId, Model model) {
        voucherService.deleteVoucher(voucherId);

        List<VoucherResponse> vouchers = voucherService.getVouchers().stream()
                .map(VoucherResponse::of)
                .toList();

        model.addAttribute("vouchers", vouchers);

        return "voucher/voucher_list";
    }
}
