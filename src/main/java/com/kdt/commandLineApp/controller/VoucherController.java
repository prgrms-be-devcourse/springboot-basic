package com.kdt.commandLineApp.controller;

import com.kdt.commandLineApp.service.customer.CustomerConverter;
import com.kdt.commandLineApp.service.customer.CustomerDTO;
import com.kdt.commandLineApp.service.voucher.VoucherConverter;
import com.kdt.commandLineApp.service.voucher.VoucherCreateDTO;
import com.kdt.commandLineApp.service.voucher.VoucherDTO;
import com.kdt.commandLineApp.service.voucher.VoucherService;
import com.kdt.commandLineApp.service.voucherWallet.VoucherWalletDTO;
import com.kdt.commandLineApp.service.voucherWallet.VoucherWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VoucherController {
    private VoucherService voucherService;
    private VoucherWalletService voucherWalletService;

    @Autowired
    public VoucherController(VoucherService voucherService, VoucherWalletService voucherWalletService) {
        this.voucherService = voucherService;
        this.voucherWalletService = voucherWalletService;
    }

    @RequestMapping(value = "/jsp", method = RequestMethod.GET)
    public String showJsp() {
        return "jsp/hello";
    }

    @GetMapping(value = "")
    public ModelAndView showVouchers() {
        List<VoucherDTO> allVouchers = voucherService.getVouchers(0, 10, null).stream().map(VoucherConverter::toVoucherDTO).toList();
        Map<String, Object> model =  Map.of("vouchers", allVouchers);

        return new ModelAndView("views/voucher", model);
    }

    @GetMapping(value = "/vouchers")
    public ModelAndView showVoucher(@RequestParam String voucherId) {
        try {
            List<VoucherDTO> voucher = List.of(VoucherConverter.toVoucherDTO(voucherService.getVoucher(Long.parseLong(voucherId)).get()));
            Map<String, Object> model =  Map.of("vouchers", voucher);
            return new ModelAndView("views/voucher", model);
        }
        catch (Exception e) {
            Map<String, Object> model =  Map.of("vouchers", List.of());
            return new ModelAndView("views/voucher", model);
        }
    }

    @GetMapping(value = "/delete")
    public ModelAndView deleteVoucher() {
        List<VoucherDTO> allVouchers = voucherService.getVouchers(0, 10, null).stream().map(VoucherConverter::toVoucherDTO).toList();
        Map<String, Object> model =  Map.of("vouchers", allVouchers);

        return new ModelAndView("views/deleteVoucher", model);
    }

    @PostMapping(value = "/delete")
    public String deleteVoucher(String voucherId) {
        voucherService.removeVoucher(Long.parseLong(voucherId));
        return "redirect:/delete";
    }

    @GetMapping(value = "/create")
    public String createVoucher() {
        return "views/createVoucher";
    }

    @PostMapping(value = "/create")
    public String createVoucher(VoucherCreateDTO voucherCreateDTO) {
        try {
            voucherService.addVoucher(voucherCreateDTO.getType(), voucherCreateDTO.getAmount());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = "/voucherWallet/customer")
    public ModelAndView showVoucherList(@RequestParam long customerId) {
        List<VoucherDTO> vouchers = voucherWalletService.getCustomerVouchers(customerId).stream().map(VoucherConverter::toVoucherDTO).toList();
        Map<String, Object> model =  new HashMap<>();

        model.put("vouchers", vouchers);
        model.put("customerId", Long.toString(customerId));
        return new ModelAndView("views/voucherList", model);
    }

    @GetMapping(value = "/voucherWallet/voucher")
    public ModelAndView showCustomerList(@RequestParam long voucherId) {
        List<CustomerDTO> customers = voucherWalletService.getCustomersWithVoucherId(voucherId).stream().map(CustomerConverter::toCustomerDTO).toList();
        Map<String, Object> model =  new HashMap<>();

        model.put("customers", customers);
        model.put("voucherId", Long.toString(voucherId));
        return new ModelAndView("views/customerList", model);
    }

    @GetMapping(value = "/voucherWallet/giveVoucher")
    public String giveVoucher() {
        return "views/giveVoucher";
    }

    @PostMapping(value = "/voucherWallet/giveVoucher")
    public String giveVoucher(VoucherWalletDTO voucherWalletDTO) {
        voucherWalletService.giveVoucherToCustomer(voucherWalletDTO.getCustomerId(), voucherWalletDTO.getVoucherId());
        return "redirect:/voucherWallet/giveVoucher";
    }

    @PostMapping(value = "/voucherWallet/takeVoucher")
    public String takeVoucher(VoucherWalletDTO voucherWalletDTO) {
        voucherWalletService.deleteVoucherFromCustomer(voucherWalletDTO.getCustomerId(), voucherWalletDTO.getVoucherId());
        return "redirect:/voucherWallet/customer?customerId="+ voucherWalletDTO.getCustomerId();
    }
}