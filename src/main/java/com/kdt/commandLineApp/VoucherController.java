package com.kdt.commandLineApp;

import com.kdt.commandLineApp.voucher.Voucher;
import com.kdt.commandLineApp.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        List<Voucher> allVouchers = voucherService.getVouchers();
        Map<String, Object> model =  Map.of("vouchers", allVouchers);

        return new ModelAndView("views/voucher", model);
    }

    @RequestMapping(value = "/voucher/{voucherId}", method = RequestMethod.GET)
    public ModelAndView showVoucher(@PathVariable String voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucher(voucherId);
        Map<String, Object> model =  Map.of("vouchers", List.of(voucher.get()));

        return new ModelAndView("views/voucher", model);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteVoucher() {
        List<Voucher> allVouchers = voucherService.getVouchers();
        Map<String, Object> model =  Map.of("vouchers", allVouchers);

        return new ModelAndView("views/deleteVoucher", model);
    }

    @RequestMapping(value = "/delete/{voucherId}", method = RequestMethod.GET)
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.removeVoucher(voucherId);
        return "redirect:/delete";
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createVoucher() {
        return "views/createVoucher";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createVoucher(HttpServletRequest httpServletRequest) {
        String type = httpServletRequest.getParameter("type");
        int amount = Integer.parseInt(httpServletRequest.getParameter("amount"));

        try {
            voucherService.addVoucher(type, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}