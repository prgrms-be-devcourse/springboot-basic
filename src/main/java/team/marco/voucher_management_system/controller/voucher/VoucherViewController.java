package team.marco.voucher_management_system.controller.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherResponse;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.domain.voucher.VoucherType;
import team.marco.voucher_management_system.service.voucher.VoucherCreateServiceRequest;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/create")
    public String findVoucherCreateForm() {
        return "voucher/voucher_create_form";
    }

    @PostMapping()
    public String createVoucher(@RequestParam("voucherType") String voucherType,
                                @RequestParam("discountValue") int discountValue,
                                Model model) {
        logger.info("createVoucher!");
        voucherService.createVoucher(new VoucherCreateServiceRequest(
                VoucherType.valueOf(voucherType),
                discountValue,
                Optional.empty(),
                Optional.empty())
        );

        List<VoucherResponse> vouchers = voucherService.getVouchers().stream()
                .map(VoucherResponse::of)
                .toList();

        model.addAttribute("vouchers", vouchers);

        return "voucher/voucher_list";
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
