package org.prgrms.assignment.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.assignment.voucher.dto.VoucherServiceRequestDTO;
import org.prgrms.assignment.voucher.dto.VoucherCreateRequestDTO;
import org.prgrms.assignment.voucher.dto.VoucherResponseDTO;
import org.prgrms.assignment.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("vouchers")
@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping()
    public String getAllVoucherDTOs(Model model) {
        List<VoucherResponseDTO> allVoucherDTOs = voucherService.getAllVoucherDTOs();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("allVoucherDTOs", allVoucherDTOs);
        return "views/vouchers";
    }

    @PostMapping("/new")
    public String createVoucher(VoucherCreateRequestDTO voucherDTO) {
        voucherService.createVoucher(VoucherServiceRequestDTO.of(voucherDTO));
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String getVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        Optional<VoucherResponseDTO> voucher = voucherService.getVoucherById(voucherId);
        model.addAttribute("serverTime", LocalDateTime.now());
        if(voucher.isPresent()) {
            model.addAttribute("voucher", voucher.get());
            return "views/voucher-details";
        }
        return "views/404";
    }

    @DeleteMapping("/delete")
    public String deleteVoucher(String voucherId) {
        voucherService.delete(UUID.fromString(voucherId));
        return "redirect:/vouchers";
    }

    @GetMapping("/new")
    public String viewNewVoucherPage() {
        return "views/new-voucher";
    }

    @GetMapping("/delete")
    public String viewDeletePage() {
        return "views/voucher-delete";
    }

}
