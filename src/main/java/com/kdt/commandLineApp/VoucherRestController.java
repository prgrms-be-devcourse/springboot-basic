package com.kdt.commandLineApp;

import com.kdt.commandLineApp.voucher.VoucherCreateDTO;
import com.kdt.commandLineApp.voucher.VoucherDTO;
import com.kdt.commandLineApp.voucher.VoucherMapper;
import com.kdt.commandLineApp.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoucherRestController {
    private VoucherService voucherService;

    @Autowired
    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @RequestMapping(value = "/api/v1/vouchers", method = RequestMethod.GET)
    public List<VoucherDTO> showVouchers() {
        return voucherService.getVouchers().stream().map(VoucherMapper::toVoucherDTO).toList();
    }

    @RequestMapping(value = "/api/v1/vouchers/{type}", method = RequestMethod.GET)
    public List<VoucherDTO> showVouchersWith(@PathVariable String type) {
        return voucherService.getVouchersWithType(type).stream().map(VoucherMapper::toVoucherDTO).toList();
    }

    @RequestMapping(value = "/api/v1/voucher/{voucherId}", method = RequestMethod.GET)
    public VoucherDTO showVoucher(@PathVariable String voucherId) {
        try {
            return VoucherMapper.toVoucherDTO(voucherService.getVoucher(voucherId).orElse(null));
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/api/v1/voucher/deleteAll", method = RequestMethod.GET)
    public String deleteVoucher() {
        voucherService.removeAllVouchers();
        return "successively delete all vouchers";
    }

    @RequestMapping(value = "/api/v1/voucher/delete/{voucherId}", method = RequestMethod.GET)
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.removeVoucher(voucherId);
        return "successively delete voucher";
    }

    @RequestMapping(value = "/api/v1/voucher/create", method = RequestMethod.POST)
    public String createVoucher(VoucherCreateDTO voucherCreateDTO) {
        try {
            voucherService.addVoucher(voucherCreateDTO.getType(), voucherCreateDTO.getAmount());
        } catch (Exception e) {
            return "error in creating voucher";
        }
        return "successively create voucher";
    }
}
