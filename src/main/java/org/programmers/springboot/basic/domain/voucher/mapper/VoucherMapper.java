package org.programmers.springboot.basic.domain.voucher.mapper;

import org.programmers.springboot.basic.AppConstants;
import org.programmers.springboot.basic.config.VoucherConfig;
import org.programmers.springboot.basic.domain.voucher.dto.CsvVoucherDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherMapper {

    private final VoucherConfig voucherConfig;

    public VoucherMapper(VoucherConfig voucherConfig) {
        this.voucherConfig = voucherConfig;
    }

    public String serialize(Voucher voucher) {

        return voucher.getVoucherId() +
                AppConstants.CSV_SEPARATOR +
                voucher.getVoucherType() +
                AppConstants.CSV_SEPARATOR +
                voucher.getDiscount();
    }

    public CsvVoucherDto deserialize(String[] token) {

        UUID voucherId = UUID.fromString(token[0]);
        VoucherType voucherType = VoucherType.valueOf(token[1]);
        Long discount = Long.parseLong(token[2]);

        return voucherConfig.getCsvVoucherDto(voucherId, voucherType, discount);
    }

    public Voucher mapToVoucher(CsvVoucherDto csvVoucherDto) {

        return switch (csvVoucherDto.voucherType()) {
            case FIXED -> voucherConfig.getFixedAmountVoucher(csvVoucherDto);
            case PERCENT -> voucherConfig.getPercentDiscountVoucher(csvVoucherDto);
        };
    }

    public Voucher mapToVoucher(VoucherRequestDto voucherRequestDto) {

        return switch (voucherRequestDto.voucherType()) {
            case FIXED -> voucherConfig.getFixedAmountVoucher(voucherRequestDto);
            case PERCENT -> voucherConfig.getPercentDiscountVoucher(voucherRequestDto);
        };
    }

    public VoucherResponseDto mapToVoucherDto(Voucher voucher) {
        return voucherConfig.getVoucherResponseDto(voucher);
    }
}
