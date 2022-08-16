package com.prgrms.kdt.springbootbasic.voucher.controller;

import com.prgrms.kdt.springbootbasic.VoucherList;
import com.prgrms.kdt.springbootbasic.dto.request.CreateVoucherRequest;
import com.prgrms.kdt.springbootbasic.dto.request.UpdateVoucherRequest;
import com.prgrms.kdt.springbootbasic.dto.response.VoucherResponseDto;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Profile("jdbc")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public List<Voucher> voucherList(@RequestParam Optional<String> voucherType, @RequestParam Optional<Boolean> ordered){
        List<String> voucherTypeList = Arrays.stream(VoucherList.values()).map(voucherList -> voucherList.getClassName()).collect(Collectors.toList());
        if (voucherType.isPresent() && voucherTypeList.contains(voucherType.get())){
            return voucherService.getVoucherByType(voucherType.get());
        }else if (ordered.isPresent() && ordered.get() == Boolean.TRUE){
            return voucherService.getVoucherOrderByCreatedAt();
        }
        else {
            return voucherService.getAllVouchers();
        }
    }

    @PostMapping("/api/v1/vouchers")
    public VoucherResponseDto createVoucher(@RequestBody CreateVoucherRequest voucherRequest){
        return VoucherResponseDto.voucherToDto(voucherService.saveVoucher(voucherRequest.voucherType(), voucherRequest.amount()));
    }

    @PutMapping("/api/v1/vouchers/{voucherId}")
    public VoucherResponseDto updateVoucher(@PathVariable("voucherId") UUID voucherId, @RequestBody UpdateVoucherRequest voucherRequest){
        Voucher voucher = VoucherList.makeVoucherByType(voucherRequest.voucherType(), voucherId, voucherRequest.amount(), LocalDateTime.now());
        return VoucherResponseDto.voucherToDto(voucherService.updateVoucher(voucher));
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public boolean deleteVoucher(@PathVariable("voucherId") UUID voucherId){
        return voucherService.deleteVoucher(voucherId);
    }
}
