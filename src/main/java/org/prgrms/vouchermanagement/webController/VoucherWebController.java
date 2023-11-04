package org.prgrms.vouchermanagement.webController;

import org.prgrms.vouchermanagement.exception.InvalidInputException;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.PolicyStatus;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@Profile("web")
@RequestMapping("/voucher")
public class VoucherWebController {

    //api path

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/voucher-home")
    public String voucherHome() {
        return "voucher/voucher-home";
    }

    @GetMapping("/create")
    public String createVoucher() {
        return "voucher/create";
    }

    @PostMapping("/create")
    public String createVoucher(@RequestParam("policy") String policy, @RequestParam("amount") long amount) {
        voucherService.createVoucher(validateAndConvertPolicy(policy), validateAndConvertAmountOrPercent(String.valueOf(amount)));

        return "redirect:/voucher/create";
    }

    @GetMapping("/find")
    public String findVoucher() {
        return "voucher/find";
    }

    //Request Param
    //추가해야 할 부분 -> update 한 번 고민해보기, 유효성 검사, Rest 알아보자
    @PostMapping("/find")
    public String findVoucher(@RequestParam("voucherId") String uuid, Model model) {
        UUID voucherId =  UUID.fromString(uuid);
        Voucher voucher = voucherService.findVoucher(voucherId);

        model.addAttribute("voucher", voucher);

        return "/voucher/find-voucher";
    }

    @GetMapping("/list")
    public String voucherList(Model model) {
        List<Voucher> vouchers = voucherService.voucherLists();
        model.addAttribute("vouchers", vouchers);
        return "voucher/list";
    }

    @GetMapping("/delete")
    public String deleteVoucher() {
        return "voucher/delete";
    }

    @PostMapping("/delete")
    public String deleteVoucher(@RequestParam("voucherId") String uuid) {
        UUID voucherId =  UUID.fromString(uuid);
        voucherService.deleteVoucher(voucherId);
        return "redirect:/voucher/delete";
    }

    //유효성 검사(null check)

    private PolicyStatus validateAndConvertPolicy(String inputPolicy) {
        try {
            return PolicyStatus.valueOf(inputPolicy.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("잘못된 policy 입력입니다.");
        }
    }

    private long validateAndConvertAmountOrPercent(String inputAmountOrPercent) {
        try {
            return Long.parseLong(inputAmountOrPercent);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("잘못된 amountOrPercent 입력입니다.");
        }
    }
}
