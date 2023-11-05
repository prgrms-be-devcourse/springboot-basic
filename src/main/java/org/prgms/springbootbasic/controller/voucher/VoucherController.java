package org.prgms.springbootbasic.controller.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherRequestDto;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
@Slf4j
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/create")
    public String showCreatePage() {
        return "voucher/voucher-create";
    }

    @PostMapping("/create")
    public String create(VoucherRequestDto voucherRequestDto) {
        log.info("voucherRequestDto = {}", voucherRequestDto);

        VoucherType voucherType = voucherService.convertToType(voucherRequestDto.voucherPolicy());

        voucherService.insert(voucherType, voucherRequestDto.discountDegree());

        return "redirect:/";
    }

    @GetMapping("/{voucherId}")
    public String showDetail(@PathVariable("voucherId") String voucherId, Model model) {
        UUID voucherUUID = UUID.fromString(voucherId);

        Voucher voucher = voucherService.findById(voucherUUID)
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("voucher", voucher);

        return "voucher/voucher-details";
    }

    @PostMapping("/{voucherId}")
    public String update(@PathVariable String voucherId, VoucherRequestDto requestDto) {
        UUID voucherUUID = UUID.fromString(voucherId);
        long discountDegree = requestDto.discountDegree();
        VoucherType voucherType = voucherService.convertToType(requestDto.voucherPolicy());

        log.info("voucherId = {}, discountDegree = {}, voucherPolicy = {}", voucherId, discountDegree, requestDto.voucherPolicy());

        voucherService.update(voucherUUID, voucherType, discountDegree);

        return "redirect:/";
    }

    @DeleteMapping("/{voucherId}")
    public void delete(@PathVariable String voucherId) {
        voucherService.deleteById(UUID.fromString(voucherId));
    }
}
