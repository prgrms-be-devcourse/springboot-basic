package org.prgrms.kdt.web.controller;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.web.dto.RequestCreateVoucherDto;
import org.prgrms.kdt.web.dto.RequestDeleteVoucherDto;
import org.prgrms.kdt.web.dto.RequestUpdateVoucherDto;
import org.prgrms.kdt.web.dto.ResponseVoucherDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/voucher")
@RestController
public class RestVoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/{voucherId}")
    public ResponseVoucherDto find(@PathVariable UUID voucherId) {
        Voucher voucher = findVoucher(voucherId);
        return mapToDto(voucher);
    }

    @GetMapping("/vouchers")
    public List<ResponseVoucherDto> vouchers() {
        List<Voucher> vouchers = getVouchers();
        return vouchers.stream()
                .map(voucher -> mapToDto(voucher))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseVoucherDto create(@RequestBody RequestCreateVoucherDto dto) {
        Voucher voucher = insertVoucher(dto);
        return mapToDto(voucher);
    }

    @DeleteMapping
    public HttpStatus delete(@ModelAttribute RequestDeleteVoucherDto dto) {
        voucherService.deleteById(dto.getVoucherId());
        return HttpStatus.ACCEPTED;
    }
    
    @PatchMapping
    public ResponseVoucherDto update(@ModelAttribute RequestUpdateVoucherDto dto) {
        Voucher voucher = updateVoucher(dto);
        return mapToDto(voucher);
    }

    private Voucher updateVoucher(RequestUpdateVoucherDto dto) {
        return voucherService.update(dto.getVoucherId(), dto.getValue());
    }

    private Voucher insertVoucher(RequestCreateVoucherDto dto) {
        Long value = dto.getValue();
        VoucherType type = VoucherType.findType(dto.getType());
        return voucherService.insert(type, value);
    }

    private ResponseVoucherDto mapToDto(Voucher voucher) {
        return new ResponseVoucherDto(voucher.getVoucherId(), voucher.getValue(), voucher.getType(), voucher.getCreatedAt());
    }

    private List<Voucher> getVouchers() {
        return voucherService.vouchers();
    }

    private Voucher findVoucher(UUID voucherId) {
        return voucherService.findById(voucherId);
    }
}
