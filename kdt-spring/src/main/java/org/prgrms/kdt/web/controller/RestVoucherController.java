package org.prgrms.kdt.web.controller;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherSearch;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.web.dto.RequestCreateVoucherDto;
import org.prgrms.kdt.web.dto.RequestSearchVoucherDto;
import org.prgrms.kdt.web.dto.RequestUpdateVoucherDto;
import org.prgrms.kdt.web.dto.ResponseVoucherDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/vouchers")
@RestController
public class RestVoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/{voucherId}")
    public ResponseVoucherDto find(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        return mapToDto(voucher);
    }

    @GetMapping
    public List<ResponseVoucherDto> vouchers() {
        List<Voucher> vouchers = voucherService.vouchers();;
        return vouchers.stream()
                .map(voucher -> mapToDto(voucher))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseVoucherDto create(@ModelAttribute RequestCreateVoucherDto dto) {
        Voucher voucher = createVoucher(dto);
        return mapToDto(voucher);
    }

    @DeleteMapping("/{voucherId}")
    public HttpStatus delete(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return HttpStatus.ACCEPTED;
    }
    
    @PatchMapping("/{voucherId}")
    public ResponseVoucherDto update(@PathVariable UUID voucherId, @ModelAttribute RequestUpdateVoucherDto dto) {
        Voucher voucher = voucherService.update(voucherId, dto.getVoucherValue());
        return mapToDto(voucher);
    }

    @GetMapping("/search")
    public List<ResponseVoucherDto> search(@ModelAttribute RequestSearchVoucherDto dto) {
        List<Voucher> vouchers = voucherService.search(dto.toVoucherSearch());
        return vouchers.stream()
                .map(v -> mapToDto(v))
                .collect(Collectors.toList());
    }


    private Voucher createVoucher(RequestCreateVoucherDto dto) {
        Long value = dto.getVoucherValue();
        VoucherType type = VoucherType.valueOf(dto.getVoucherType());
        return voucherService.insert(type, value);
    }

    private ResponseVoucherDto mapToDto(Voucher voucher) {
        return new ResponseVoucherDto(voucher.getVoucherId(), voucher.getValue(), voucher.getType(), voucher.getCreatedAt());
    }
}
