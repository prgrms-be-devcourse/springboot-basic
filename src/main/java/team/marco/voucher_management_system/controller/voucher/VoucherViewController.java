package team.marco.voucher_management_system.controller.voucher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherResponse;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.domain.voucher.VoucherType;
import team.marco.voucher_management_system.service.voucher.VoucherCreateServiceRequest;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.List;

@Controller
@RequestMapping("/vouchers")
public class VoucherViewController {
    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String findAllVouchers(Model model) {
        getVouchersAndAddToModel(model);

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

    @PostMapping
    public String createVoucher(@RequestParam("voucherType") String voucherType,
                                @RequestParam("discountValue") int discountValue,
                                Model model) {
        if (isNotPositive(discountValue)) throw new IllegalArgumentException("할인 금액 또는 할인율은 양수입니다.");

        voucherService.createVoucher(new VoucherCreateServiceRequest(
                VoucherType.valueOf(voucherType),
                discountValue)
        );

        getVouchersAndAddToModel(model);

        return "voucher/voucher_list";
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable Long voucherId, Model model) {
        voucherService.deleteVoucher(voucherId);

        getVouchersAndAddToModel(model);

        return "voucher/voucher_list";
    }

    private void getVouchersAndAddToModel(Model model) {
        List<VoucherResponse> vouchers = vouchersToVoucherResponses(voucherService.getVouchers());
        model.addAttribute("vouchers", vouchers);
    }

    private List<VoucherResponse> vouchersToVoucherResponses(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(VoucherResponse::of)
                .toList();
    }

    private static boolean isNotPositive(int discountValue) {
        return discountValue <= 0;
    }
}
