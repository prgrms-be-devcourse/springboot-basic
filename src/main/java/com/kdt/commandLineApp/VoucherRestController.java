package com.kdt.commandLineApp;

import com.kdt.commandLineApp.voucher.Voucher;
import com.kdt.commandLineApp.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class VoucherRestController {
    private VoucherService voucherService;

    @Autowired
    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @RequestMapping(value = "/api/v1/vouchers", method = RequestMethod.GET)
    public List<Voucher> showVouchers() {
        return voucherService.getVouchers();
    }

    @RequestMapping(value = "/api/v1/voucher/{voucherId}", method = RequestMethod.GET)
    public Voucher showVoucher(@PathVariable String voucherId) {
        return voucherService.getVoucher(voucherId).get();
    }

    @RequestMapping(value = "/api/v1/voucher/deleteAll", method = RequestMethod.GET)
    public void deleteVoucher() {
        voucherService.removeAllVouchers();
        return;
    }

    @RequestMapping(value = "/api/v1/voucher/{voucherId}/delete", method = RequestMethod.GET)
    public void deleteVoucher(@PathVariable String voucherId) {
        voucherService.removeVoucher(voucherId);
        return;
    }

    @RequestMapping(value = "/api/v1/voucher/create", method = RequestMethod.POST)
    public void createVoucher(HttpServletRequest httpServletRequest) {
        String type = httpServletRequest.getParameter("type");
        int amount = Integer.parseInt(httpServletRequest.getParameter("amount"));

        try {
            voucherService.addVoucher(type, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}
