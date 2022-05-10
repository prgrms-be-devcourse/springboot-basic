package com.prgrms.voucher_manager.voucher.controller;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.controller.CustomerController;
import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import com.prgrms.voucher_manager.infra.facade.VoucherServiceFacade;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.service.VoucherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final VoucherServiceFacade voucherServiceFacade;
    private final VoucherService voucherService;

    public VoucherController(VoucherServiceFacade voucherServiceFacade, VoucherService voucherService) {
        this.voucherServiceFacade = voucherServiceFacade;
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public String Vouchers(Model model) {
        List<VoucherDto> vouchers = voucherService.getFindAllVoucher();
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucher(@PathVariable UUID voucherId, Model model) {
        VoucherDto voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher";
    }

    @GetMapping("/add")
    public String addVoucherForm() {
        return "voucher/addVoucher";
    }

    @PostMapping("/add")
    public String addVoucher(@RequestParam String type, @RequestParam long value) {

        voucherService.createVoucher(type, value);

        return "redirect:/vouchers";
    }

    @GetMapping("/date")
    public String findVoucherByDateAndTypeForm(Model model) {
        return "voucher/findByDateAndTypeForm";
    }

    @PostMapping("/date")
    public String findVoucherByDateAndType(@RequestParam String type,
                                           @RequestParam String start,
                                           @RequestParam String end,
                                           Model model) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<VoucherDto> vouchers = voucherService.findByDateAndType(startDate, endDate, type);
        model.addAttribute("vouchers", vouchers);
        return "voucher/findByDateAndType";
    }

    @GetMapping("/{voucherId}/update")
    public String updateVoucherForm(@PathVariable UUID voucherId, Model model) {
        VoucherDto voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/updateVoucher";
    }

    @PostMapping("/{voucherId}/update")
    public String updateVoucher(@PathVariable UUID voucherId,
                                @RequestParam String type,
                                 @RequestParam long value) {


        voucherService.updateVoucher(voucherId, type, value);

        return "redirect:/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherType}/customer")
    public String findCustomerByVoucherType(@PathVariable String voucherType, Model model) {
        List<CustomerDto> customers = voucherServiceFacade.findCustomerByVoucherType(voucherType);
        model.addAttribute("customers", customers);
        return "voucher/customers";
    }

}
