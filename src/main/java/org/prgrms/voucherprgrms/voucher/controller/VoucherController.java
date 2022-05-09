package org.prgrms.voucherprgrms.voucher.controller;

import org.prgrms.voucherprgrms.voucher.VoucherService;
import org.prgrms.voucherprgrms.voucher.model.VoucherDTO;
import org.prgrms.voucherprgrms.voucher.model.VoucherForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping("/vouchers")
    public String viewVoucherListPage(Model model) {
        List<VoucherDTO> vouchers = voucherService.findAllVoucher().stream().map(VoucherDTO::toVoucherDTO).collect(Collectors.toList());
        model.addAttribute("vouchers", vouchers);

        return "views/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String findVoucher(@PathVariable("voucherId") String voucherId, Model model) {
        var dto = VoucherDTO.toVoucherDTO(voucherService.findByVoucherId(voucherId));
        model.addAttribute("voucher", dto);
        return "views/voucher-details";
    }

    @PostMapping("/vouchers/new")
    public String createVoucher(@ModelAttribute VoucherForm voucherForm) {
        voucherService.createVoucher(voucherForm);
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewVoucherCreatePage(Model model) {
        model.addAttribute(new VoucherForm());
        return "views/new-voucher";
    }

    @GetMapping("/vouchers/{voucherId}/delete")
    public String deleteVoucher(@PathVariable("voucherId") String voucherId) {
        try {
            voucherService.deleteVoucher(voucherId);
        } catch (IllegalArgumentException e) {
            logger.error("Delete {} failed.", voucherId, e);
            return "views/404";
        }
        return "redirect:/vouchers";
    }
}
