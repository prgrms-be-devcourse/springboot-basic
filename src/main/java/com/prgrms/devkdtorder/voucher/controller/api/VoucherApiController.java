package com.prgrms.devkdtorder.voucher.controller.api;

import com.prgrms.devkdtorder.util.ApiUtils;
import com.prgrms.devkdtorder.voucher.dto.VoucherDto;
import com.prgrms.devkdtorder.voucher.dto.VoucherListDto;
import com.prgrms.devkdtorder.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.prgrms.devkdtorder.util.ApiUtils.ApiResponse;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {

    private VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ApiResponse<VoucherListDto> findAllVouchers() {
        var voucherDtoList = voucherService.getAllVouchers().stream()
                .map(VoucherDto::new).collect(Collectors.toList());
        return ApiUtils.success(new VoucherListDto(voucherDtoList));
    }

    @GetMapping(path = "{voucherId}")
    public ApiResponse<VoucherDto> findVoucherById(@PathVariable("voucherId") UUID voucherId) {
        return ApiUtils.success(new VoucherDto(voucherService.getVoucher(voucherId)));
    }

    @PostMapping
    public ApiResponse<Boolean> saveVoucher(@RequestBody VoucherDto voucherDto) {
        voucherService.saveVoucher(voucherDto.toEntity());
        return ApiUtils.success(true);
    }

    @PutMapping
    public ApiResponse<Boolean> updateVoucher(@RequestBody VoucherDto voucherDto) {
        voucherService.updateVoucher(voucherDto.toEntity());
        return ApiUtils.success(true);
    }

    @DeleteMapping(path = "{voucherId}")
    public ApiResponse<Boolean> deleteVoucherById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return ApiUtils.success(true);
    }

}
