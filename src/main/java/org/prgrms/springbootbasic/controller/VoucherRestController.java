package org.prgrms.springbootbasic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbootbasic.dto.VoucherInputDto;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.exception.VoucherNotFoundException;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.util.UUIDUtil;
import org.prgrms.springbootbasic.validator.VoucherInputDtoValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherRestController {
    private final VoucherService voucherService;
    private final VoucherInputDtoValidator voucherInputDtoValidator;

    @InitBinder("VoucherInputDto")
    public void init(WebDataBinder dataBinder) {
        log.info("init binder {}", dataBinder);
        dataBinder.addValidators(voucherInputDtoValidator);
    }

    @PostMapping(value = "/new")
    public Voucher createVoucher(@Validated @RequestBody VoucherInputDto voucherInputDto) {
        return voucherService.createVoucher(voucherInputDto);
    }

    @GetMapping(value = "/{voucherId}")
    public Voucher getVoucher(@PathVariable String voucherId) {
        if (!UUIDUtil.isUUID(voucherId)) {
            throw new VoucherNotFoundException();
        }
        return voucherService.getVoucherById(voucherId);
    }

    @GetMapping(value = "/list")
    public List<Voucher> getVoucherList() {
        return voucherService.getVoucherList();
    }
}
