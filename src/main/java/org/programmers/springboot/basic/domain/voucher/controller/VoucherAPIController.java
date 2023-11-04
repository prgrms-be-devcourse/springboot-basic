package org.programmers.springboot.basic.domain.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.springboot.basic.AppConstants;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherControllerRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("default")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
public class VoucherAPIController {

    private final VoucherService voucherService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody VoucherControllerRequestDto requestDto) {
        VoucherType voucherType = VoucherType.valueOf(requestDto.getVoucherType());
        Long discount = Long.parseLong(requestDto.getDiscount());

        VoucherRequestDto voucherRequestDto = VoucherMapper.INSTANCE.mapToRequestDto(voucherType, discount);
        voucherService.create(voucherRequestDto);
        return ResponseEntity.ok(AppConstants.SUCCESS);
    }

    @GetMapping("/{voucherId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VoucherResponseDto> findVoucherById(@PathVariable String voucherId) {
        VoucherRequestDto requestDto = VoucherMapper.INSTANCE.mapToRequestDtoWithUUID(voucherId);
        VoucherResponseDto responseDto = voucherService.findById(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<VoucherResponseDto>> findVoucher(
            @RequestParam(name = "voucherId", required = false) String voucherId,
            @RequestParam(name = "createdAt", required = false) String createdAt,
            @RequestParam(name = "discount", required = false) String discount,
            @RequestParam(name = "voucherType", required = false) String voucherType
    ) {
        VoucherRequestDto voucherRequestDto = VoucherMapper.INSTANCE.mapToRequestDtoWithAllArgs(
                voucherId, voucherType, discount, createdAt
        );
        List<VoucherResponseDto> voucherResponseDtos = voucherService.findByOption(voucherRequestDto);
        return ResponseEntity.ok(voucherResponseDtos);
    }

    @GetMapping("/vouchers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<VoucherResponseDto>> findAllVoucher() {
        List<VoucherResponseDto> vouchers = voucherService.findAll();
        return ResponseEntity.ok(vouchers);
    }

    @PatchMapping("/{discount}")
    public ResponseEntity<String> updateVoucher(@PathVariable Long discount,
                                              @RequestBody VoucherControllerRequestDto requestDto) {
        VoucherRequestDto voucherRequestDto = VoucherMapper.INSTANCE
                .mapToRequestDtoWithIdNDiscount(requestDto.getVoucherId(), discount);
        voucherService.updateVoucher(voucherRequestDto);
        return ResponseEntity.ok(AppConstants.SUCCESS);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<String> deleteVoucher(@PathVariable String voucherId) {
        VoucherRequestDto voucherRequestDto = VoucherMapper.INSTANCE.mapToRequestDtoWithUUID(voucherId);
        voucherService.deleteVoucher(voucherRequestDto);
        return ResponseEntity.ok(AppConstants.SUCCESS);
    }
}
