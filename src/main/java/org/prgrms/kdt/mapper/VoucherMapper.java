package org.prgrms.kdt.mapper;

import java.time.format.DateTimeFormatter;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherDto;
import org.prgrms.kdt.voucher.VoucherType;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:38 오전
 */
public interface VoucherMapper {

    static VoucherDto voucherToVoucherDto(Voucher voucher) {
        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setVoucherId(voucher.getVoucherId().toString());
        voucherDto.setName(voucher.getName());

        if (voucher.getVoucherType() == VoucherType.FIX) {
            voucherDto.setDiscount(voucher.getDiscount() + "원");
        }

        if (voucher.getVoucherType() == VoucherType.PERCENT) {
            voucherDto.setDiscount(voucher.getDiscount() + "%");
        }

        voucherDto.setVoucherType(voucher.getVoucherType().name());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        voucherDto.setCreatedAt(formatter.format(voucher.getCreatedAt()));
        return voucherDto;
    }

}
