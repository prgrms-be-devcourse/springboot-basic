package com.example.voucher_manager.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherViewController {

    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherViewController.class);

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // ===== VIEWS =====
    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        List<VoucherDto> convertObject = voucherService.findAll().stream()
                .map(VoucherDto::from)
                .toList();
        model.addAttribute("vouchers", convertObject);
        return "views/voucher/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String findVoucher(@PathVariable UUID voucherId, Model model) {
        var findVoucher = voucherService.findVoucher(voucherId);
        if (findVoucher.isPresent()) {
            model.addAttribute("voucher", VoucherDto.from(findVoucher.get()));
            return "views/voucher/voucher-details";
        }
        return "views/error/404";
    }

    @PostMapping("/vouchers/{voucherId}")
    public String modifyVoucher(VoucherDto voucherDto) {
        logger.info("Got voucher data modify request {}", voucherDto);
        voucherService.updateVoucher(VoucherDto.toEntity(voucherDto));
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewCreateVoucherPage() {
        return "views/voucher/voucher-new";
    }

    @PostMapping("/vouchers/new")
    public String createVoucherPage(CreateVoucherRequest createVoucherRequest) {
        logger.info("Got create voucher request {}", createVoucherRequest);
        voucherService.createVoucher(
                VoucherType.of(createVoucherRequest.voucherType()),
                createVoucherRequest.discountInfo());
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/remove/{voucherId}")
    public String removeVoucher(@PathVariable UUID voucherId) {
        logger.info("Got voucher remove request {}", voucherId);
        if (voucherService.deleteVoucher(voucherId)) {
            return "redirect:/vouchers";
        }
        return "views/error/504";
    }
}
