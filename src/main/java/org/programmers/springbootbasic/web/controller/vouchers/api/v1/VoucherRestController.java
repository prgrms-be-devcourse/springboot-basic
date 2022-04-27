package org.programmers.springbootbasic.web.controller.vouchers.api.v1;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.web.controller.vouchers.VoucherCreateForm;
import org.programmers.springbootbasic.web.controller.vouchers.VoucherDto;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class VoucherRestController {

    private final VoucherService voucherService;

    @GetMapping("/api/v1/vouchers")
    public List<VoucherDto> getAllVouchers(@RequestParam(required = false) VoucherType type,
                                           @RequestParam(required = false) Date startingDate, @RequestParam(required = false)
    Date endingDate) {
        if (type!=null) {
            if (startingDate != null && endingDate != null) {
                return voucherService.getVouchersByTypeAndDate(type, startingDate, endingDate)
                        .stream().map(voucher -> VoucherDto.from(voucher))
                        .collect(toList());
            }
            return voucherService.getVouchersByType(type)
                    .stream().map(voucher -> VoucherDto.from(voucher))
                    .collect(toList());
        }
        if (startingDate != null && endingDate != null) {
            return voucherService.getVouchersByDate(startingDate, endingDate)
                    .stream().map(voucher -> VoucherDto.from(voucher))
                    .collect(toList());
        }
        return voucherService.getAllVouchers()
                .stream().map(voucher -> VoucherDto.from(voucher))
                .collect(toList());
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    public VoucherDto getAVoucherById(@PathVariable UUID voucherId) {
        return VoucherDto.from(voucherService.getVoucher(voucherId));
    }

    @PostMapping("/api/v1/vouchers")
    public VoucherDto createVoucher(@RequestBody VoucherCreateForm createForm) {
        return VoucherDto.from(voucherService.createVoucher(createForm.getAmount(), createForm.getType()));
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public void deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }
}
