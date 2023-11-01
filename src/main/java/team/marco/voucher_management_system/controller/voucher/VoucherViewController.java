package team.marco.voucher_management_system.controller.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherResponse;
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
}
