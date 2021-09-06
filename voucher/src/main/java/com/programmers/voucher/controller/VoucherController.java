package com.programmers.voucher.controller;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherService voucherService;

    private static final List<String[]> links = new ArrayList<>();
    private static final String LINKS_MODEL_ATTRIBUTE = "links";
    private static final String ERROR_MODEL_ATTRIBUTE = "error";

    static {
        links.add(new String[]{"Main", "/voucher"});
        links.add(new String[]{"Create Voucher", "/voucher/create"});
        links.add(new String[]{"Read Voucher", "/voucher/read"});
        links.add(new String[]{"List Vouchers", "/voucher/list"});
        links.add(new String[]{"Update Vouchers", "/voucher/update"});
        links.add(new String[]{"Delete Voucher", "/voucher/delete"});
    }

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/create")
    public String requestCreateVoucher(Model model) {
        DiscountPolicy.Type[] availableDiscountPolicies = DiscountPolicy.Type.values();
        model.addAttribute("availableDiscountPolicies", availableDiscountPolicies);
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        return "voucher/create";
    }

    @PostMapping("/create")
    public String submitCreateVoucher(@RequestParam(name = "name", defaultValue = "") String name,
                                      @RequestParam(name = "type", defaultValue = "") String type,
                                      @RequestParam(name = "amount", defaultValue = "") Integer amount,
                                      @RequestParam(name = "owner", defaultValue = "") Long owner,
                                      Model model) {
        DiscountPolicy.Type[] availableDiscountPolicies = DiscountPolicy.Type.values();
        model.addAttribute("availableDiscountPolicies", availableDiscountPolicies);
        model.addAttribute("name", name);
        model.addAttribute("type", type);
        model.addAttribute("amount", amount);
        model.addAttribute("owner", owner);

        if(name.isBlank() || type.isBlank() || amount == null || amount < 0 || owner == null || owner < 1) {
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Required fields cannot be empty.");
            return "voucher/create";
        }

        Voucher voucher = voucherService.create(name, DiscountPolicy.Type.of(type), amount, owner);
        // currently when DataAccessException, which is SQLIntegrityConstraintViolationException is thrown
        // when customer id(foreign key) does not match constraint. And by repository implementation it returns
        // voucher object with id set '-1'.
        if(voucher.getId() < 0) {
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Couldn't create voucher. Please check your customer id.");
            return "voucher/create";
        }

        return "redirect:/voucher/read?id=" + voucher.getId();
    }

    @GetMapping("/read")
    public String readVoucher(@RequestParam(name = "id", defaultValue = "") Long id,
                              Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        if (id == null) return "voucher/read";
        model.addAttribute("id", id);
        voucherService.findById(id).ifPresentOrElse(
                voucher -> model.addAttribute("voucher", voucher),
                () -> model.addAttribute(ERROR_MODEL_ATTRIBUTE, "No voucher found."));
        return "voucher/read";
    }

}
