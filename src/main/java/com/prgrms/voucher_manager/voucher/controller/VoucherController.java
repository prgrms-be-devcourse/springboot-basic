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


import java.util.ArrayList;
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
        List<Voucher> vouchers = voucherService.getFindAllVoucher();
        List<VoucherDto> voucherDtos = new ArrayList<>();
        vouchers.forEach(voucher -> voucherDtos.add(voucher.toVoucherDto()));
        model.addAttribute("vouchers", voucherDtos);
        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucher(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        VoucherDto voucherDto = voucher.toVoucherDto();
        model.addAttribute("voucher", voucherDto);
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

    @GetMapping("/{voucherId}/update")
    public String updateVoucherForm(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        VoucherDto voucherDto = voucher.toVoucherDto();
        model.addAttribute("voucher", voucherDto);
        return "voucher/updateVoucher";
    }

    @PostMapping("/{voucherId}/update")
    public String updateVoucher(@PathVariable UUID voucherId,
                                @RequestParam String type,
                                 @RequestParam long value) {

        Voucher updateVoucher = voucherService.findById(voucherId);
        updateVoucher.changeValue(value);
        voucherService.updateVoucher(updateVoucher);

        return "redirect:/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        Voucher deleteVoucher = voucherService.findById(voucherId);
        voucherService.deleteVoucher(deleteVoucher);
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherType}/customer")
    public String findCustomerByVoucherType(@PathVariable String voucherType, Model model) {
        List<Customer> customers = voucherServiceFacade.findCustomerByVoucherType(voucherType);
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.forEach(customer -> customerDtos.add(customer.toCustomerDto()));
        if(customers.isEmpty()) {
            logger.info("해당 바우처 타입을 가진 고객이 없습니다");
            return "redirect:/vouchers";
        }
        else {
            model.addAttribute("customers", customerDtos);
            return "voucher/customers";
        }

    }

}
