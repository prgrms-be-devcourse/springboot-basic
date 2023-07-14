package com.programmers.voucher.web;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {
    private final VoucherStream voucherStream;

    public VoucherController(VoucherStream voucherStream) {
        this.voucherStream = voucherStream;
    }

    @GetMapping
    public String voucherList(Model model) {
        Map<String, Voucher> voucherMap = voucherStream.findAll();
        ArrayList<Voucher> voucherArrayList = new ArrayList<>();
        voucherMap.forEach(
                (k, v) -> {
                    voucherArrayList.add(v);
                }
        );
        model.addAttribute("voucherArrayList", voucherArrayList);
        return "/voucher/list";
    }

    @GetMapping("/add")
    public String createVoucher() {
        return "/voucher/create/voucher";
    }

    @PostMapping("/add")
    public String createVoucher(@RequestParam Integer discount,
                                @RequestParam String type,
                                Model model) {

        if ("FixedAmountVoucher".equals(type)) {
            FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID().toString().substring(0, 7), discount);
            voucherStream.save(voucher);
            model.addAttribute("voucher", voucher);
            return "redirect:/vouchers/" + voucher.getVoucherId();
        } else if ("PercentDiscountVoucher".equals(type)) {
            PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID().toString().substring(0, 7), discount);
            voucherStream.save(voucher);
            model.addAttribute("voucher", voucher);
            return "redirect:/vouchers/" + voucher.getVoucherId();
        } else {
            return "redirect:/vouchers";
        }
    }

    @GetMapping("/{voucherId}")
    public String voucherInformation(@PathVariable String voucherId, Model model) {
        Voucher voucher = voucherStream.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "/voucher/information/voucher-information";
    }

    @GetMapping("/fixed/{voucherId}/edit")
    public String editFixedAmountVoucher(@PathVariable String voucherId, Model model) {
        FixedAmountVoucher voucher = (FixedAmountVoucher) voucherStream.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "/voucher/edit/fixed-editForm";
    }

    @PostMapping("/fixed/{voucherId}/edit")
    public String editFixedAmountVoucher(@PathVariable String voucherId,
                                         @RequestParam Integer amount,
                                         Model model) {
        Voucher voucher = voucherStream.findById(voucherId);
        voucher.update(amount);
        voucherStream.update(voucher);
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers/fixed/" + voucherId;
    }

    @GetMapping("/rate/{voucherId}/edit")
    public String editPercentDiscountVoucher(@PathVariable String voucherId, Model model) {
        PercentDiscountVoucher voucher = (PercentDiscountVoucher) voucherStream.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "/voucher/edit/rate-editForm";
    }

    @PostMapping("/rate/{voucherId}/edit")
    public String editPercentDiscountVoucher(@PathVariable String voucherId,
                                             @RequestParam Integer rate,
                                             Model model) {
        Voucher voucher = voucherStream.findById(voucherId);
        voucher.update(rate);
        voucherStream.update(voucher);
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers/rate/" + voucherId;
    }
}
