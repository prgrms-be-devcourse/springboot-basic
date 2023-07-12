package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.persistent.VouchersStorage;
import com.prgms.springbootbasic.util.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VouchersStorage vouchersStorage;

    public VoucherController(VouchersStorage vouchersStorage) {
        this.vouchersStorage = vouchersStorage;
    }

    @GetMapping("/vouchers")
    public String findVouchers(Model model) {
        try {
            List<Voucher> vouchers = vouchersStorage.findAll();
            model.addAttribute("vouchers", vouchers);
            return "views/vouchers";
        } catch (Exception e) {
            return "views/403";
        }
    }

    @GetMapping("/vouchers/new")
    public String viewFormNewVouchers() {
        return "views/new-vouchers";
    }

    @PostMapping("/vouchers/new")
    public String createNewVouchers(NewVoucherDto newVoucherDto) {
        try {
            VoucherType type = VoucherType.of(newVoucherDto.getType());
            Voucher voucher =  type.createNewVoucher(newVoucherDto.getAmount());
            vouchersStorage.save(voucher);
            return "redirect:/vouchers";
        } catch (Exception e) {
            return "views/403";
        }
    }

    @GetMapping("/vouchers/update/{voucherId}")
    public String viewFormUpdateVouchers(@PathVariable("voucherId") UUID voucherId, Model model) {
        model.addAttribute("voucherId", voucherId);
        return "views/update-vouchers";
    }

    @PostMapping("/vouchers/update/{voucherId}")
    public String updateVouchers(@PathVariable("voucherId") UUID voucherId, ModifiedVoucherDto modifiedVoucherDto) {
        try {
            Voucher voucher = vouchersStorage.findById(voucherId);
            voucher.changeAmount(modifiedVoucherDto.getAmount());
            vouchersStorage.update(voucher);
            return "redirect:/vouchers";
        } catch (Exception e) {
            return "views/403";
        }
    }

    @GetMapping("/vouchers/delete/{voucherId}")
    public String deleteVouchers(@PathVariable("voucherId") UUID voucherId) {
        try {
            vouchersStorage.deleteOne(voucherId);
            return "redirect:/vouchers";
        } catch (Exception e) {
            return "views/403";
        }
    }

    static class NewVoucherDto {
        private String type;
        private Long amount;

        public NewVoucherDto(String type, Long amount) {
            this.type = type;
            this.amount = amount;
        }

        public String getType() { return type; }
        public Long getAmount() { return amount; }
    }

    static class ModifiedVoucherDto {
        private Long amount;

        public ModifiedVoucherDto(Long amount) {
            this.amount = amount;
        }

        public Long getAmount() {
            return amount;
        }
    }

}
