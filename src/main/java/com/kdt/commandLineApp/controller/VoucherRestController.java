package com.kdt.commandLineApp.controller;

import com.kdt.commandLineApp.service.voucher.VoucherCreateDTO;
import com.kdt.commandLineApp.service.voucher.VoucherDTO;
import com.kdt.commandLineApp.service.voucher.VoucherConverter;
import com.kdt.commandLineApp.service.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VoucherRestController {
    private VoucherService voucherService;

    @Autowired
    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping(value = "/vouchers")
    public List<VoucherDTO> showVouchers(@RequestParam(defaultValue = "0", required = false)String page, @RequestParam(defaultValue = "10", required = false) String size, @RequestParam(required = false) String type) {
        System.out.println(page+type+size);
        return voucherService.getVouchers(Integer.parseInt(page),Integer.parseInt(size), type).stream().map(VoucherConverter::toVoucherDTO).toList();
    }

    @GetMapping(value = "/vouchers/{voucherId}")
    public VoucherDTO showVoucher(@PathVariable String voucherId) {
        try {
            return VoucherConverter.toVoucherDTO(voucherService.getVoucher(Long.parseLong(voucherId)).orElse(null));
        }
        catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping(value = "/vouchers/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.removeVoucher(Long.parseLong(voucherId));
        return "successively delete voucher";
    }

    @PostMapping(value = "/vouchers")
    public String createVoucher(@RequestBody VoucherCreateDTO voucherCreateDTO) {
        try {
            voucherService.addVoucher(voucherCreateDTO.getType(), voucherCreateDTO.getAmount());
        } catch (Exception e) {
            return "error in creating voucher";
        }
        return "successively create voucher";
    }
}
