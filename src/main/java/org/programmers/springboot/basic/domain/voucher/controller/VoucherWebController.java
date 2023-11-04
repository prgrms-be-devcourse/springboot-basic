package org.programmers.springboot.basic.domain.voucher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherControllerRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.exception.DuplicateVoucherException;
import org.programmers.springboot.basic.domain.voucher.exception.IllegalDiscountException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/voucher")
public class VoucherWebController {

    private final VoucherService voucherService;

    @GetMapping("/createForm")
    public String createVoucher() {
        return "voucher/createForm";
    }

    @GetMapping("/findForm")
    public String findVoucher() {
        return "voucher/findForm";
    }

    @GetMapping("/updateForm")
    public String updateVoucher() {
        return "voucher/updateForm";
    }

    @GetMapping("/deleteForm")
    public String deleteVoucher() {
        return "voucher/deleteForm";
    }

    @PostMapping("/create")
    public String create(VoucherControllerRequestDto requestDto, Model model) {

        VoucherType voucherType = null;
        Long discount = null;
        try {
            voucherType = VoucherType.valueOf(Integer.parseInt(requestDto.getVoucherType()));
            discount = Long.parseLong(requestDto.getDiscount());
            VoucherRequestDto voucherRequestDto = VoucherMapper.INSTANCE.mapToRequestDto(voucherType, discount);
            voucherService.create(voucherRequestDto);
            return "redirect:/";
        } catch (NumberFormatException e) {
            log.error("your input is Invalid Discount Type '{}'", discount);
            model.addAttribute("err", e.getMessage());
        } catch (IllegalDiscountException e) {
            log.error("your input is Invalid Discount Range '{}'", discount);
            model.addAttribute("err", e.getMessage());
        } catch (DuplicateVoucherException e) {
            log.warn("voucher of voucherType '{}' and discount '{} is already exists",
                    voucherType, discount);
            model.addAttribute("err", e.getMessage());
        } catch (VoucherNotFoundException e) {
            log.warn("No matching voucher of voucherType '{}' found", voucherType);
            model.addAttribute("err", e.getMessage());
        }
        return "error";
    }

    @GetMapping("/find")
    public String find(@RequestParam String voucherId, Model model) {
        try {
            VoucherRequestDto voucherRequestDto = VoucherMapper.INSTANCE.mapToRequestDtoWithUUID(voucherId);
            VoucherResponseDto voucherResponseDto = voucherService.findById(voucherRequestDto);
            model.addAttribute("voucher", voucherResponseDto);
            return "voucher/find";
        } catch (IllegalArgumentException e) {
            log.error("Your input is Invalid UUID string: '{}'", voucherId);
            model.addAttribute("err", e.getMessage());
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher found for voucherId: '{}'", voucherId);
            model.addAttribute("err", e.getMessage());
        }
        return "error";
    }

    @PutMapping("/update")
    public String update(VoucherControllerRequestDto requestDto, Model model) {
        String voucherId = null;
        Long discount = null;
        try {
            voucherId = requestDto.getVoucherId();
            discount = Long.parseLong(requestDto.getDiscount());
            VoucherRequestDto voucherRequestDto = VoucherMapper.INSTANCE.mapToRequestDtoWithIdNDiscount(voucherId, discount);
            voucherService.updateVoucher(voucherRequestDto);
            return "redirect:/";
        } catch (NumberFormatException e) {
            log.error("Your input is Invalid Type of Long: '{}'", discount);
            model.addAttribute("err", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Your input is Invalid UUID string: '{}'", voucherId);
            model.addAttribute("err", e.getMessage());
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", voucherId);
            model.addAttribute("err", e.getMessage());
        }
        return "error";
    }

    @DeleteMapping("/delete")
    public String delete(VoucherControllerRequestDto requestDto, Model model) {
        String voucherId = null;
        try {
            voucherId = requestDto.getVoucherId();
            VoucherRequestDto voucherRequestDto = VoucherMapper.INSTANCE.mapToRequestDtoWithUUID(voucherId);
            voucherService.deleteVoucher(voucherRequestDto);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            log.error("Your input is Invalid UUID string: '{}'", voucherId);
            model.addAttribute("err", e.getMessage());
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", voucherId);
            model.addAttribute("err", e.getMessage());
        }
        return "error";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<VoucherResponseDto> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucherList";
    }
}
