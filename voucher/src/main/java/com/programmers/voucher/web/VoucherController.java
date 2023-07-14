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

    @GetMapping("/version")
    public String chooseVoucherType() {
        return "/voucher/version";
    }

    @GetMapping("/fixed/add")
    public String createFixedAmountVoucher() {
        return "/voucher/create/fixed";
    }

    @PostMapping("/fixed/add")
    public String createFixedAmountVoucher(@RequestParam Integer amount, Model model) {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID().toString().substring(0, 7), amount);
        voucherStream.save(voucher);
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers/fixed/" + voucher.getVoucherId();
    }

    @GetMapping("/rate/add")
    public String createPercentDiscountVoucher() {
        return "/voucher/create/rate";
    }

    @PostMapping("/rate/add")
    public String createPercentDiscountVoucher(@RequestParam Integer rate, Model model) {
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID().toString().substring(0, 7), rate);
        voucherStream.save(voucher);
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers/rate/" + voucher.getVoucherId();
    }

    @GetMapping("/fixed/{voucherId}")
    public String fixedVoucherInformation(@PathVariable String voucherId, Model model) {
        FixedAmountVoucher voucher = (FixedAmountVoucher) voucherStream.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "/voucher/information/fixed-information";
    }

    @GetMapping("/rate/{voucherId}")
    public String rateVoucherInformation(@PathVariable String voucherId, Model model) {
        PercentDiscountVoucher voucher = (PercentDiscountVoucher) voucherStream.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "/voucher/information/rate-information";
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
