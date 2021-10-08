package com.programmers.voucher.controller;

import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.entity.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.entity.voucher.dto.VoucherUpdateRequest;
import com.programmers.voucher.service.voucher.VoucherService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    public static final String REDIRECT_TO = "redirect:";
    private final VoucherService voucherService;

    private static final List<String[]> links = new ArrayList<>();
    private static final String LINKS_MODEL_ATTRIBUTE = "links";
    private static final String ERROR_MODEL_ATTRIBUTE = "error";
    private static final String VOUCHER_MODEL_ATTRIBUTE = "voucher";
    private static final String DISCOUNT_POLICIES_MODEL_ATTRIBUTE = "availableDiscountPolicies";

    private static final String VIEW_CREATE_VOUCHER = "voucher/create";
    private static final String VIEW_READ_VOUCHER = "voucher/read";
    private static final String VIEW_UPDATE_VOUCHER = "voucher/update";

    private static final String URL_CREATE_VOUCHER = "/voucher/create";
    public static final String URL_READ_VOUCHER = "/voucher/read";
    public static final String URL_LIST_VOUCHER = "/voucher/list";

    static {
        links.add(new String[]{"Main", "/"});
        links.add(new String[]{"Voucher Main", "/voucher"});
        links.add(new String[]{"Create Voucher", URL_CREATE_VOUCHER});
        links.add(new String[]{"Read Voucher", URL_READ_VOUCHER});
        links.add(new String[]{"List Vouchers", URL_LIST_VOUCHER});
    }

    private static final DiscountType[] availableDiscountPolicies = DiscountType.values();


    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String voucherConsole(Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        return "voucher/index";
    }

    @GetMapping("/create")
    public String requestCreateVoucher(Model model) {
        model.addAttribute(DISCOUNT_POLICIES_MODEL_ATTRIBUTE, availableDiscountPolicies);
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        return VIEW_CREATE_VOUCHER;
    }

    @PostMapping("/create")
    public String submitCreateVoucher(VoucherCreateRequest request,
                                      Model model) {
        model.addAttribute(DISCOUNT_POLICIES_MODEL_ATTRIBUTE, availableDiscountPolicies);
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);

        String name = request.getName();
        DiscountType type = DiscountType.of(request.getType());
        int amount = request.getAmount();
        Long ownerId = request.getOwner();

        model.addAttribute("amount", amount);
        model.addAttribute("owner", ownerId);
        try {
            Voucher voucher = voucherService.create(name, type, amount, ownerId);
            return "redirect:/voucher/read?id=" + voucher.getId();
        } catch (DataAccessException exception) {
            // currently when DataAccessException, which is SQLIntegrityConstraintViolationException is thrown
            // when customer id(foreign key) does not match constraint. And by repository implementation it returns
            // voucher object with id set '-1'.
            model.addAttribute("name", name);
            model.addAttribute("type", type);
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, "Couldn't create voucher. Please check your customer id.");
            return VIEW_CREATE_VOUCHER;
        } catch (IllegalArgumentException exception) {
            model.addAttribute("name", "");
            model.addAttribute("type", "");
            model.addAttribute(ERROR_MODEL_ATTRIBUTE, exception.getMessage());
            return VIEW_CREATE_VOUCHER;
        }
    }

    @GetMapping("/list")
    public String listVouchers(Model model) {
        model.addAttribute("vouchers", voucherService.listAll());
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        return "voucher/list";
    }

    @GetMapping("/read")
    public String readVoucher(@RequestParam(name = "id", defaultValue = "") Long id,
                              Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        if (id != null) {
            model.addAttribute("id", id);
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
        AtomicBoolean voucherExists = new AtomicBoolean(false);
        voucherService.findById(id).ifPresent(voucher -> {
            model.addAttribute(VOUCHER_MODEL_ATTRIBUTE, voucher);
            voucherExists.set(true);
        });
        return voucherExists.get() ? VIEW_UPDATE_VOUCHER : REDIRECT_TO + URL_LIST_VOUCHER;
    }

    @PostMapping("/update")
    public String submitUpdateVoucher(VoucherUpdateRequest request,
                                      Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        model.addAttribute(DISCOUNT_POLICIES_MODEL_ATTRIBUTE, availableDiscountPolicies);

        long id = request.getId();
        long ownerId = request.getOwner();
        String name = request.getName();
        String type = request.getType();
        int amount = request.getAmount();
        try {
            voucherService.update(id, name, DiscountType.of(type), amount, ownerId);
            return REDIRECT_TO + URL_READ_VOUCHER + "?id=" + id;
        } catch (IllegalArgumentException ex) {
            AtomicBoolean voucherExists = new AtomicBoolean(true);
            voucherService.findById(id).ifPresentOrElse(
                    voucher -> {
                        voucherExists.set(true);
                        model.addAttribute(VOUCHER_MODEL_ATTRIBUTE, voucher);
                    },
                    () -> voucherExists.set(false));

            return voucherExists.get() ? VIEW_UPDATE_VOUCHER : REDIRECT_TO + URL_LIST_VOUCHER;
        }
    }

    @GetMapping("/delete")
    public String deleteVoucher(@RequestParam(name = "id", defaultValue = "") Long id) {
        if (id != null) voucherService.delete(id);
        return REDIRECT_TO + URL_LIST_VOUCHER;
    }
}
