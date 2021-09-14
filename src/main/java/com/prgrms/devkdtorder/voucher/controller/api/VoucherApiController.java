package com.prgrms.devkdtorder.voucher.controller.api;

import com.prgrms.devkdtorder.util.ApiUtils;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.domain.VoucherType;
import com.prgrms.devkdtorder.voucher.dto.VoucherDto;
import com.prgrms.devkdtorder.voucher.dto.VoucherListDto;
import com.prgrms.devkdtorder.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    private VoucherListDto mapToVoucherListDto(List<Voucher> vouchers) {
        List<VoucherDto> voucherDtoList = vouchers.stream().map(VoucherDto::new).collect(Collectors.toList());
        return new VoucherListDto(voucherDtoList);
    }

    @GetMapping
    public ApiResponse<VoucherListDto> findAllVouchers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromCreatedAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toCreatedAt,
            @RequestParam(required = false) VoucherType voucherType
    ) {
        if (fromCreatedAt != null && toCreatedAt != null) {
            var from = LocalDateTime.of(fromCreatedAt.getYear(), fromCreatedAt.getMonth(), fromCreatedAt.getDayOfMonth(), 0, 0);
            var to = LocalDateTime.of(toCreatedAt.getYear(), toCreatedAt.getMonth(), toCreatedAt.getDayOfMonth(), 0, 0);
            return ApiUtils.success(mapToVoucherListDto(voucherService.getVouchersByCreatedAt(from, to)));
        }
        if (voucherType != null) {
            return ApiUtils.success(mapToVoucherListDto(voucherService.getVouchersByVoucherType(voucherType)));
        }
        return ApiUtils.success(mapToVoucherListDto(voucherService.getAllVouchers()));
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
