package org.prgrms.kdt.voucher;

import org.prgrms.kdt.command.VoucherExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app/vouchers")
public class VoucherController {

    private final VoucherExecutor voucherExecutor;

    public VoucherController(VoucherExecutor voucherExecutor) {
        this.voucherExecutor = voucherExecutor;
    }

    @GetMapping
    public String vouchers(Model model) {
        List<Voucher> vouchers = voucherExecutor.list();
        model.addAttribute("vouchers", vouchers);
        return "app/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucher(@PathVariable long voucherId, Model model) {

        Optional<Voucher> maybeVoucher = voucherExecutor.findVoucher(voucherId);

        model.addAttribute("voucher", maybeVoucher.get());
        return "app/voucher";
    }

    @GetMapping("/add")
    public String addForm() {
        return "app/addForm";
    }

    @PostMapping("/add")
    public String addVoucher(@RequestParam String type, @RequestParam String amount, Model model) {
        Voucher voucher = voucherExecutor.create(type, amount);
        model.addAttribute("voucher", voucher);
        return "app/voucher";
    }

    @PostMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable(value = "voucherId") long voucherId) {
        voucherExecutor.deleteVoucher(voucherId);
        return "redirect:/app/vouchers";
    }

}
