package org.prgms.kdtspringweek1.thymeleafController;

import org.prgms.kdtspringweek1.voucher.service.VoucherService;
import org.prgms.kdtspringweek1.voucher.service.dto.CreateVoucherRequestDto;
import org.prgms.kdtspringweek1.voucher.service.dto.FindVoucherResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class ThymeleafVoucherController {

    private final VoucherService voucherService;

    public ThymeleafVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String listVouchers(Model model) {
        List<FindVoucherResponseDto> vouchers = voucherService.searchAllVouchers();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher-list";
    }

    @PostMapping("/voucher")
    public String createVoucher(@ModelAttribute CreateVoucherRequestDto createVoucherRequestDto) {
        voucherService.registerVoucher(createVoucherRequestDto.toVoucher());
        return "voucher/voucher-create";
    }

    @PostMapping("/voucher-delete")
    public String deleteVoucher(@RequestParam String voucherId) {
        voucherService.deleteVoucherById(UUID.fromString(voucherId));
        return "voucher/voucher-delete";
    }

    @GetMapping("/voucher")
    public String searchVoucher(@RequestParam String voucherId, Model model) {
        FindVoucherResponseDto voucher = voucherService.searchVoucherById(UUID.fromString(voucherId)).get();
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-search";
    }

    @GetMapping("/voucher-create")
    public String createVoucher() {
        return "voucher/voucher-create";
    }

    @GetMapping("/voucher-delete")
    public String deleteVoucher() {
        return "voucher/voucher-delete";
    }

    @GetMapping("/voucher-search")
    public String searchVoucher() {
        return "voucher/voucher-search";
    }

}
