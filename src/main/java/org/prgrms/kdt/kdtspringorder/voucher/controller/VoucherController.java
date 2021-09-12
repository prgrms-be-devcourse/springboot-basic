package org.prgrms.kdt.kdtspringorder.voucher.controller;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdt.kdtspringorder.voucher.dto.VoucherDto;
import org.prgrms.kdt.kdtspringorder.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public ModelAndView list() {
        final List<Voucher> vouchers = this.voucherService.getVouchers();
        return new ModelAndView("views/vouchers", Map.of("vouchers", vouchers));
    }

    @GetMapping("/voucher/detail/{voucherId}")
    public ModelAndView detail(@PathVariable("voucherId") UUID voucherId) {
        final Voucher voucher = this.voucherService.getVoucher(voucherId);
        return new ModelAndView("views/voucherDetail", Map.of("voucher", voucher));
    }

    @GetMapping("/voucher/new")
    public ModelAndView createForm() {
        final VoucherDto.Create createForm = new VoucherDto.Create();
        return new ModelAndView("views/voucherCreateForm", Map.of("createForm", createForm));
    }

    @PostMapping("/voucher/new")
    public ModelAndView create(VoucherDto.Create voucherDto) {
        logger.info("[Param] voucherDto = " + voucherDto.toString());
        this.voucherService.saveVoucher(voucherDto.getVoucherType(), voucherDto.getAmount());
        logger.info("Voucher Create Success!!");
        return new ModelAndView("redirect:/vouchers");
    }

    @GetMapping("/voucher/delete/{voucherId}")
    public ModelAndView delete(@PathVariable("voucherId") UUID voucherId) {
        this.voucherService.deleteVoucher(voucherId);
        logger.info("Voucher Delete Success!!");
        return new ModelAndView("redirect:/vouchers");
    }

}
