package com.kdt.commandLineApp.controller;

import com.kdt.commandLineApp.voucher.VoucherCreateDTO;
import com.kdt.commandLineApp.voucher.VoucherDTO;
import com.kdt.commandLineApp.voucher.VoucherMapper;
import com.kdt.commandLineApp.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showVouchers() {
        List<VoucherDTO> allVouchers = voucherService.getVouchers().stream().map(VoucherMapper::toVoucherDTO).toList();
        Map<String, Object> model =  Map.of("vouchers", allVouchers);

        return new ModelAndView("views/voucher", model);
    }

    @RequestMapping(value = "/voucher", method = RequestMethod.GET)
    public ModelAndView showVoucher(@RequestParam String voucherId) {
        try {
            List<VoucherDTO> voucher = List.of(VoucherMapper.toVoucherDTO(voucherService.getVoucher(voucherId).get()));
            Map<String, Object> model =  Map.of("vouchers", voucher);
            return new ModelAndView("views/voucher", model);
        }
        catch (Exception e) {
            Map<String, Object> model =  Map.of("vouchers", List.of());
            return new ModelAndView("views/voucher", model);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteVoucher() {
        List<VoucherDTO> allVouchers = voucherService.getVouchers().stream().map(VoucherMapper::toVoucherDTO).toList();
        Map<String, Object> model =  Map.of("vouchers", allVouchers);

        return new ModelAndView("views/deleteVoucher", model);
    }

    @PostMapping(value = "/delete", consumes={MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String deleteVoucher(@RequestParam String voucherId) {
        voucherService.removeVoucher(voucherId);
        return "redirect:/delete";
    }

    @RequestMapping(value = "/create", method=RequestMethod.GET)
    public String createVoucher() {
        return "views/createVoucher";
    }

    @PostMapping(value = "/create", consumes={MediaType.APPLICATION_FORM_URLENCODED_VALUE})
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