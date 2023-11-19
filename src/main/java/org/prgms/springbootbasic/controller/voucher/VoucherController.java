package org.prgms.springbootbasic.controller.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.service.dto.VoucherCreateRequestDto;
import org.prgms.springbootbasic.service.dto.VoucherInsertDto;
import org.prgms.springbootbasic.service.dto.VoucherResponseDto;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.service.VoucherService;
import org.prgms.springbootbasic.service.dto.VoucherUpdateDto;
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
    public String create(VoucherCreateRequestDto voucherCreateRequestDto) {
        VoucherInsertDto voucherInsertDto = new VoucherInsertDto(voucherCreateRequestDto.voucherPolicy(),
                voucherCreateRequestDto.discountDegree());

        voucherService.insert(voucherInsertDto);

        return "redirect:/";
    }

    @GetMapping("/{voucherId}")
    public String showDetail(@PathVariable("voucherId") String voucherId, Model model) {
        UUID voucherUUID = UUID.fromString(voucherId);

        VoucherResponseDto voucherResponseDto = voucherService.findById(voucherUUID)
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("voucher", voucherResponseDto);

        return "voucher/voucher-details";
    }

    @PostMapping("/{voucherId}")
    public String update(@PathVariable String voucherId, VoucherCreateRequestDto requestDto) {
        UUID voucherUUID = UUID.fromString(voucherId);

        VoucherUpdateDto voucherUpdateDto = new VoucherUpdateDto(voucherUUID,
                requestDto.voucherPolicy(),
                requestDto.discountDegree());

        voucherService.update(voucherUpdateDto);

        return "redirect:/";
    }

    @DeleteMapping("/{voucherId}")
    public void delete(@PathVariable String voucherId) { // dto 공부. 계층간 전송. dto는 패키지가 어디에 있어야 하는지
        voucherService.deleteById(UUID.fromString(voucherId));
    }
}
