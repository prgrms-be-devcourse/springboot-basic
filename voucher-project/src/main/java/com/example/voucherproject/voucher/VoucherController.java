package com.example.voucherproject.voucher;

import com.example.voucherproject.common.exception.ErrorCode;
import com.example.voucherproject.voucher.dto.VoucherDTO;
import com.example.voucherproject.voucher.exception.VoucherNotFoundException;
import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.model.VoucherType;
import com.example.voucherproject.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Slf4j
public class VoucherController {

    private final VoucherService voucherService;

    // 바우처 생성
    @GetMapping("/voucher")
    public String addVoucherView(Model model){
        model.addAttribute("dto",new VoucherDTO.Create());
        return "voucher/voucher-new";
    }

    // 바우처 생성
    @PostMapping("/voucher")
    public String addVoucherRedirect(@Validated @ModelAttribute VoucherDTO.Create dto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("dto", dto);
            bindingResult.addError(new FieldError("dto", "amount","입력형식을 맞춰주세요."+bindingResult.getObjectName()));
            log.info("errors = {}",bindingResult);
            return "voucher/voucher-new";
        }

       voucherService.createVoucher(dto);
        return "redirect:/vouchers";
    }

    // 바우처 목록 조회
    @GetMapping("/vouchers")
    public String vouchersView(Model model){
        var vouchers = voucherService.findAll();
        vouchers.sort(Comparator.comparing(Voucher::getCreatedAt));
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }

    // 바우처 상세 조회
    @GetMapping("/voucher/detail/{id}")
    public String voucherDetailView(@PathVariable UUID id, Model model){
        var maybeVoucher = voucherService.findById(id);

        if (maybeVoucher.isPresent()){
            model.addAttribute("voucher", maybeVoucher.get());
            return "voucher/voucher-detail";
        }

        throw new VoucherNotFoundException(String.format("%s voucher not found",id));
    }

    // 바우처 삭제
    @DeleteMapping("/voucher/{id}")
    public String deleteVoucher(@PathVariable UUID id){
        voucherService.deleteById(id);
        return "redirect:../vouchers/";
    }

    // 바우처 수정
    @GetMapping("/voucher/{id}")
    public String editVoucherView(@PathVariable UUID id, Model model){
        var maybeVoucher = voucherService.findById(id);
        System.out.println(maybeVoucher.get());
        model.addAttribute("voucher", maybeVoucher.get());
        return "voucher/voucher-edit";
    }

    // 바우처 수정
    @PostMapping("/voucher/{id}")
    public String editVoucher(@ModelAttribute VoucherDTO.Update dto,BindingResult bindingResult){
        voucherService.updateDTO(dto);
        return "redirect:../vouchers/";
    }

    // 바우처 필터링
    @PostMapping("/voucher/query")
    public String queryVoucher(@ModelAttribute VoucherDTO.Query query, Model model){
        System.out.println(query);

        List<Voucher> vouchers = voucherService.findByTypeAndDate(query);
        model.addAttribute("vouchers", vouchers);
        return "voucher/vouchers";
    }
}
