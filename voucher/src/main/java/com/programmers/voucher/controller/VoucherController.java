package com.programmers.voucher.controller;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.service.customer.CustomerService;
import com.programmers.voucher.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherService voucherService;
    private final CustomerService customerService;

    private static final List<String[]> links = new ArrayList<>();
    private static final String LINKS_MODEL_ATTRIBUTE = "links";
    private static final String ERROR_MODEL_ATTRIBUTE = "error";
    private static final String VOUCHER_MODEL_ATTRIBUTE = "voucher";
    private static final String DISCOUNT_POLICIES_MODEL_ATTRIBUTE = "availableDiscountPolicies";
    private static final String VIEW_CREATE_VOUCHER = "voucher/create";
    private static final String VIEW_READ_VOUCHER = "voucher/read";
    private static final String VIEW_UPDATE_VOUCHER = "voucher/update";

    static {
        links.add(new String[]{"Main", "/voucher"});
        links.add(new String[]{"Create Voucher", "/VIEW_CREATE_VOUCHER"});
        links.add(new String[]{"Read Voucher", "/voucher/read"});
        links.add(new String[]{"List Vouchers", "/voucher/list"});
        links.add(new String[]{"Update Vouchers", "/voucher/update"});
        links.add(new String[]{"Delete Voucher", "/voucher/delete"});
    }

    private static final DiscountPolicy.Type[] availableDiscountPolicies = DiscountPolicy.Type.values();


    public VoucherController(VoucherService voucherService, CustomerService basicCustomerService) {
        this.voucherService = voucherService;
        this.customerService = basicCustomerService;
    }

    @GetMapping("/create")
    public String requestCreateVoucher(Model model) {
        model.addAttribute(DISCOUNT_POLICIES_MODEL_ATTRIBUTE, availableDiscountPolicies);
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        return VIEW_CREATE_VOUCHER;
    }

    @PostMapping("/create")
    public String submitCreateVoucher(@RequestParam(name = "name", defaultValue = "") String name,
                                      @RequestParam(name = "type", defaultValue = "") String type,
                                      @RequestParam(name = "amount", defaultValue = "") Integer amount,
                                      @RequestParam(name = "owner", defaultValue = "") Long owner,
                                      Model model) {
        model.addAttribute(DISCOUNT_POLICIES_MODEL_ATTRIBUTE, availableDiscountPolicies);
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute("name", name);
        model.addAttribute("type", type);
        model.addAttribute("amount", amount);
        model.addAttribute("owner", owner);

        if (name.isBlank() || type.isBlank() || amount == null || amount < 0 || owner == null || owner < 1) {
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Required fields cannot be empty.");
            return VIEW_CREATE_VOUCHER;
        }

        Voucher voucher = voucherService.create(name, DiscountPolicy.Type.of(type), amount, owner);
        // currently when DataAccessException, which is SQLIntegrityConstraintViolationException is thrown
        // when customer id(foreign key) does not match constraint. And by repository implementation it returns
        // voucher object with id set '-1'.
        if (voucher.getId() < 0) {
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Couldn't create voucher. Please check your customer id.");
            return VIEW_CREATE_VOUCHER;
        }

        return "redirect:/voucher/read?id=" + voucher.getId();
    }

    @GetMapping("/read")
    public String readVoucher(@RequestParam(name = "id", defaultValue = "") Long id,
                              Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute("id", id);
        if (id != null) {
            voucherService.findById(id).ifPresentOrElse(
                    voucher -> model.addAttribute(VOUCHER_MODEL_ATTRIBUTE, voucher),
                    () -> model.addAttribute(ERROR_MODEL_ATTRIBUTE, "No voucher found."));
        }
        return VIEW_READ_VOUCHER;
    }

    @GetMapping("/update")
    public String requestUpdateVoucher(@RequestParam(name = "id", defaultValue = "") Long id,
                                       Model model) {
        if (id == null) return "redirect:/voucher";
        model.addAttribute(DISCOUNT_POLICIES_MODEL_ATTRIBUTE, availableDiscountPolicies);
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        Optional<Voucher> byId = voucherService.findById(id);
        if (byId.isPresent()) model.addAttribute(VOUCHER_MODEL_ATTRIBUTE, byId.get());
        else {
            return "redirect:/voucher/list";
        }

        return VIEW_UPDATE_VOUCHER;
    }

    @PostMapping("/update")
    public String submitUpdateVoucher(@RequestParam(name = "id", defaultValue = "0") long id,
                                      @RequestParam(name = "name", defaultValue = "") String name,
                                      @RequestParam(name = "type", defaultValue = "") String type,
                                      @RequestParam(name = "amount", defaultValue = "0") int amount,
                                      @RequestParam(name = "owner", defaultValue = "0") long owner,
                                      Model model) {
        Optional<Voucher> voucher = voucherService.findById(id);
        Optional<Customer> customer = customerService.findById(owner);
        if (voucher.isEmpty()) return "redirect:/voucher/list";

        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        Voucher updatedVoucher = voucher.get();
        if (name.isBlank() || type.isBlank()) {
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Required fields cannot be empty.");
            model.addAttribute(DISCOUNT_POLICIES_MODEL_ATTRIBUTE, availableDiscountPolicies);
            model.addAttribute(VOUCHER_MODEL_ATTRIBUTE, updatedVoucher);
            return VIEW_UPDATE_VOUCHER;
        }
        updatedVoucher.setName(name);
        updatedVoucher.setDiscountPolicy(new DiscountPolicy(amount, DiscountPolicy.Type.of(type)));

        if (customer.isEmpty()) {
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Customer not exists.");
            model.addAttribute(DISCOUNT_POLICIES_MODEL_ATTRIBUTE, availableDiscountPolicies);
            model.addAttribute(VOUCHER_MODEL_ATTRIBUTE, updatedVoucher);
            return VIEW_UPDATE_VOUCHER;
        }
        updatedVoucher.setCustomerId(owner);
        voucherService.update(updatedVoucher);

        return "redirect:/voucher/read?id=" + updatedVoucher.getId();
    }
}
