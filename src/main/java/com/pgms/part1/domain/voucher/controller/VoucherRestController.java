package com.pgms.part1.domain.voucher.controller;

import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherWebCreateRequestDto;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.voucher.service.VoucherService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public List<VoucherResponseDto> getVouchersList(){
        return voucherService.listVoucher();
    }

    @GetMapping("/vouchers/search")
    public List<VoucherResponseDto> findVouchersByFilter(@RequestParam(value = "date", required = false) String date,
                                                         @RequestParam(value = "type", required = false) VoucherDiscountType type){
        return voucherService.findVouchersByCreatedDate(date, type);
    }

    @PostMapping("/vouchers")
    public VoucherResponseDto createVoucher(@Validated @RequestBody VoucherWebCreateRequestDto voucherWebCreateRequestDto){
        return voucherService.createVoucher(voucherWebCreateRequestDto);
    }

    @DeleteMapping("/vouchers/{id}")
    public void deleteVoucher(@PathVariable Long id){
        voucherService.deleteVoucher(id);
    }

    @GetMapping("/vouchers/{id}")
    public VoucherResponseDto getVoucherById(@PathVariable Long id){
        return voucherService.getVoucherById(id);
    }
}
