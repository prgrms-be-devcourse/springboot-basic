package org.prgms.kdt.application.voucher.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prgms.kdt.application.voucher.controller.dto.VoucherRequestDto;
import org.prgms.kdt.application.voucher.controller.dto.VoucherResponseDto;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
public class VoucherApiController {

    private final VoucherService voucherService;

    @GetMapping()
    public List<VoucherResponseDto> getAllVouchers() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        return vouchers.stream()
            .map((voucher) -> new VoucherResponseDto(voucher))
            .collect(Collectors.toList());
    }

    // TODO : 응답 message 개선
    @PostMapping()
    public String createVoucher(@RequestBody VoucherRequestDto voucherRequestDto) {
        voucherService.createVoucher(voucherRequestDto.getVoucher());
        return "success";
    }

    @GetMapping("/{voucherId}")
    public VoucherResponseDto getVoucherByVoucherId(@PathVariable UUID voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucherByVoucherId(voucherId);
        return new VoucherResponseDto(voucher.get());
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteByVoucherId(voucherId);
        return "success";
    }

}
