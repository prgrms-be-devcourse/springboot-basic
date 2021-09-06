package org.prgrms.kdt.mapper;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherDto;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:38 오전
 */
public interface VoucherMapper {

    static VoucherDto voucherToVoucherDto(Voucher voucher) {
        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setVoucherId(voucher.getVoucherId().toString());
        voucherDto.setName(voucher.getName());
        voucherDto.setDiscount(voucher.getDiscount());
        voucherDto.setVoucherType(voucher.getVoucherType().name());
        voucherDto.setCreatedAt(voucher.getCreatedAt().toString());
        return voucherDto;
    }
}
