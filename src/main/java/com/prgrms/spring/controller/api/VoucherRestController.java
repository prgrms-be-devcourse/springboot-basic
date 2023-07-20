package com.prgrms.spring.controller.api;

import com.google.protobuf.Api;
import com.prgrms.spring.common.dto.ApiResponse;
import com.prgrms.spring.controller.dto.request.VoucherCreateRequestDto;
import com.prgrms.spring.controller.dto.response.VoucherResponseDto;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.exception.Success;
import com.prgrms.spring.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/voucher")
public class VoucherRestController {

    private final VoucherService voucherService;

    // 1. 전체 조회 기능
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<VoucherResponseDto>> getAllVoucher() {
        return ApiResponse.success(Success.GET_VOUCHER_SUCCESS, voucherService.getAllVoucher());
    }

    // 2. 조건별 조회 기능
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<VoucherResponseDto>> getSearchedVoucher(@RequestParam String type) {
        VoucherType voucherType = VoucherType.valueOf(type);
        return ApiResponse.success(Success.GET_VOUCHER_SUCCESS, voucherService.getSearchedVoucher(voucherType));
    }

    // 3. 바우처 추가기능
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VoucherResponseDto> createVoucher(@RequestBody VoucherCreateRequestDto requestDto) {
        return ApiResponse.success(Success.CREATE_VOUCHER_SUCCESS, voucherService.createVoucher(requestDto));
    }

    // 4. 바우처 삭제기능

    // 5. 바우처 아이디로 조회 기능
}
