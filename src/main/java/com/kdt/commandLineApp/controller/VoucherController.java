package com.kdt.commandLineApp.controller;

import com.kdt.commandLineApp.service.voucher.VoucherConverter;
import com.kdt.commandLineApp.service.voucher.VoucherCreateDTO;
import com.kdt.commandLineApp.service.voucher.VoucherDTO;
import com.kdt.commandLineApp.service.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class VoucherController {
    private VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
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
    public String deleteVoucher(@RequestParam String voucherId) {
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
}