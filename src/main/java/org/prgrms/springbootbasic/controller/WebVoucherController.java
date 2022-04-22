package org.prgrms.springbootbasic.controller;

import java.util.UUID;
import org.prgrms.springbootbasic.dto.CreateVoucherRequest;
import org.prgrms.springbootbasic.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebVoucherController {

    private static final Logger logger = LoggerFactory.getLogger(WebVoucherController.class);
    private final VoucherService voucherService;

    public WebVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        var voucherDTOs = voucherService.findAll();
        model.addAttribute("voucherDTOs", voucherDTOs);
        return "vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String viewVoucherPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        var voucherDTO = voucherService.findVoucher(voucherId);
        model.addAttribute("voucher", voucherDTO);
        return "voucher";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage(Model model) {
        model.addAttribute(new CreateVoucherRequest());
        return "new-voucher";
    }

    @PostMapping("/vouchers/new")
    public String createVoucher(
        @ModelAttribute CreateVoucherRequest createVoucherRequest,
        BindingResult bindingResult) {
        logger.info("Got createVoucherRequest -> {}", createVoucherRequest);

        if (createVoucherRequest.getVoucherType().isFixed()) {
            validateFixedVoucherInput(createVoucherRequest, bindingResult);
        }
        if (createVoucherRequest.getVoucherType().isPercent()) {
            validatePercentVoucherInput(createVoucherRequest, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            logger.info("errors={}", bindingResult);
            return "new-voucher";
        }

        voucherService.createVoucher(
            createVoucherRequest.getVoucherType(),
            createVoucherRequest.getAmount() == null ? 0 : createVoucherRequest.getAmount(),
            createVoucherRequest.getPercent() == null ? 0 : createVoucherRequest.getPercent());
        return "redirect:/vouchers";
    }

    private void validatePercentVoucherInput(CreateVoucherRequest createVoucherRequest,
        BindingResult bindingResult) {
        if (createVoucherRequest.getPercent() == null) {
            bindingResult.rejectValue("percent", "required");
        }
        if (createVoucherRequest.getAmount() != null) {
            bindingResult.rejectValue("amount", "notRequired");
        }
    }

    private void validateFixedVoucherInput(CreateVoucherRequest createVoucherRequest,
        BindingResult bindingResult) {
        if (createVoucherRequest.getAmount() == null) {
            bindingResult.rejectValue("amount", "required");
        }
        if (createVoucherRequest.getPercent() != null) {
            bindingResult.rejectValue("percent", "notRequired");
        }
    }
}
