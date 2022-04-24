package org.prgms;

import org.prgms.voucher.domain.Voucher;
import org.prgms.voucher.domain.VoucherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherRepository voucherRepository;

    public VoucherController(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @GetMapping("/vouchers")
    public String vouchers(Model model) {
        model.addAttribute("vouchers", voucherRepository.findAll());
        return "vouchers.html";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String vouchers(@PathVariable UUID voucherId, Model model, HttpServletResponse httpServletResponse) {
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucherId);
        if (voucherOptional.isEmpty()) {
            return "redirect:vouchers.html";
        }
        model.addAttribute("voucher", voucherOptional);
        return "voucher-detail.html";
    }

//    @GetMapping("/error")
//    public void error(){
//        System.out.println("hello");
//    }
}
