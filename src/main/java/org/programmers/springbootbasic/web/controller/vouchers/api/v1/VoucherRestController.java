package org.programmers.springbootbasic.web.controller.vouchers.api.v1;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.programmers.springbootbasic.web.dto.VoucherCreateForm;
import org.programmers.springbootbasic.web.dto.VoucherDto;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class VoucherRestController {

    private final VoucherService voucherService;

    @GetMapping("/api/v1/vouchers")
    public List<VoucherDto> getAllVouchers(@RequestParam(required = false) VoucherType type,
                                           @RequestParam(required = false) Date startingDate, @RequestParam(required = false)
                                                   Date endingDate) {
        if (type != null) {
            if (startingDate != null && endingDate != null) {
                return voucherService.getVouchersByTypeAndDate(type, startingDate, endingDate)
                        .stream().map(VoucherDto::from)
                        .toList();
            }
            return voucherService.getVouchersByType(type)
                    .stream().map(VoucherDto::from)
                    .toList();
        }
        if (startingDate != null && endingDate != null) {
            return voucherService.getVouchersByDate(startingDate, endingDate)
                    .stream().map(VoucherDto::from)
                    .toList();
        }
        return voucherService.getAllVouchers()
                .stream().map(VoucherDto::from)
                .toList();
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    public VoucherDto getAVoucherById(@PathVariable UUID voucherId) {
        return VoucherDto.from(voucherService.getVoucher(voucherId));
    }

    @PostMapping("/api/v1/vouchers")
    public VoucherDto createVoucher(@RequestBody VoucherCreateForm createForm) {
        return VoucherDto.from(voucherService.createVoucher(createForm.getAmount(), createForm.getType()));
    }

    @ResponseStatus(OK)
    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public void deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }
}