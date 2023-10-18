package org.programmers.springboot.basic.config;

import org.programmers.springboot.basic.domain.voucher.dto.CsvVoucherDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.FixedAmountVoucher;
import org.programmers.springboot.basic.domain.voucher.entity.PercentDiscountVoucher;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.service.validate.FixedAmountVoucherValidator;
import org.programmers.springboot.basic.domain.voucher.service.validate.PercentDiscountVoucherValidator;
import org.programmers.springboot.basic.domain.voucher.service.validate.ValidateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class VoucherConfig {

    private final FixedAmountVoucherValidator fixedAmountVoucherValidator;
    private final PercentDiscountVoucherValidator percentDiscountVoucherValidator;

    @Autowired
    public VoucherConfig(FixedAmountVoucherValidator fixedAmountVoucherValidator, PercentDiscountVoucherValidator percentDiscountVoucherValidator) {
        this.fixedAmountVoucherValidator = fixedAmountVoucherValidator;
        this.percentDiscountVoucherValidator = percentDiscountVoucherValidator;
    }

    public Voucher getFixedAmountVoucher(VoucherRequestDto requestDto) {
        return new FixedAmountVoucher(UUID.randomUUID(), requestDto.discount(), requestDto.voucherType());
    }

    public Voucher getFixedAmountVoucher(CsvVoucherDto csvVoucherDto) {
        return new FixedAmountVoucher(csvVoucherDto.voucherId(), csvVoucherDto.discount(), csvVoucherDto.voucherType());
    }

    public Voucher getPercentDiscountVoucher(VoucherRequestDto requestDto) {
        return new PercentDiscountVoucher(UUID.randomUUID(), requestDto.discount(), requestDto.voucherType());
    }

    public Voucher getPercentDiscountVoucher(CsvVoucherDto csvVoucherDto) {
        return new PercentDiscountVoucher(csvVoucherDto.voucherId(), csvVoucherDto.discount(), csvVoucherDto.voucherType());
    }

    public VoucherResponseDto getVoucherResponseDto(Voucher voucher) {
        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount());
    }

    public CsvVoucherDto getCsvVoucherDto(UUID voucherId, VoucherType voucherType, Long discount) {
        return new CsvVoucherDto(voucherId, voucherType, discount);
    }

    @Bean
    public Map<VoucherType, ValidateHandler> validateHandlers() {
        Map<VoucherType, ValidateHandler> handler = new HashMap<>();
        handler.put(VoucherType.FIXED, this.fixedAmountVoucherValidator);
        handler.put(VoucherType.PERCENT, this.percentDiscountVoucherValidator);
        return handler;
    }
}
