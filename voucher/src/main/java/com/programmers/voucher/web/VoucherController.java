package com.programmers.voucher.web;

import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {
    private final VoucherStream voucherStream;
    private final VoucherFactory voucherFactory;

    public VoucherController(VoucherStream voucherStream, VoucherFactory voucherFactory) {
        this.voucherStream = voucherStream;
        this.voucherFactory = voucherFactory;
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
        return "voucher/voucher-addForm";
    }

    @PostMapping("/add")
    public String createVoucher(@RequestParam Integer discount,
                                @RequestParam String type,
                                Model model) {
        return voucherFactory.create(discount, type, model);
    }

    @GetMapping("/{voucherId}")
    public String voucherInformation(@PathVariable String voucherId, Model model) {
        Voucher voucher = voucherStream.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "/voucher/voucher-information";
    }

    @GetMapping("/{voucherId}/edit")
    public String editVoucher(@PathVariable String voucherId, Model model) {
        Voucher voucher = voucherStream.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "/voucher/voucher-editForm";
    }

    @PostMapping("/{voucherId}/edit")
    public String editVoucher(@PathVariable String voucherId,
                              @RequestParam Integer discount,
                              Model model) {
        Voucher voucher = voucherStream.findById(voucherId);
        voucher.update(discount);
        voucherStream.update(voucher);
        model.addAttribute("voucher", voucher);
        return "redirect:/vouchers/" + voucherId;
    }
}
