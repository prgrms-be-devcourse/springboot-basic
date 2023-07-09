package org.prgrms.assignment.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.assignment.voucher.dto.VoucherRequestDTO;
import org.prgrms.assignment.voucher.dto.VoucherResponseDTO;
import org.prgrms.assignment.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;

    @GetMapping("/vouchers")
    public String getAllVoucherDTOs(Model model) {
        List<VoucherResponseDTO> allVoucherDTOs = voucherService.getAllVoucherDTOs();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("allVoucherDTOs", allVoucherDTOs);
        return "views/vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucherPage() {
        return "views/new-voucher";
    }


    @PostMapping("/vouchers/new")
    public String createVoucher(VoucherRequestDTO voucherDTO) {
        voucherService.createVoucher(voucherDTO.voucherType(), voucherDTO.benefit(), voucherDTO.durationDate());
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String getVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        Optional<VoucherResponseDTO> voucher = voucherService.getVoucherById(voucherId);
        model.addAttribute("serverTime", LocalDateTime.now());
        if(voucher.isPresent()) {
            model.addAttribute("voucher", voucher.get());
            return "views/voucher-details";
        }
        return "views/404";
    }
}
