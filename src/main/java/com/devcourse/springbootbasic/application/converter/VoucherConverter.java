package com.devcourse.springbootbasic.application.converter;

import com.devcourse.springbootbasic.application.domain.voucher.Voucher;
import com.devcourse.springbootbasic.application.dto.DiscountValue;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class VoucherConverter {

    private VoucherConverter() {}

    public static List<String> convertToStringList(List<Voucher> list) {
        return list.stream()
                .map(Voucher::toString)
                .toList();
    }

    public static Voucher convertDtoToVoucher(VoucherDto voucherDto, UUID uuid) {
        return new Voucher(uuid, voucherDto.voucherType(), voucherDto.discountValue());
    }

    public static String convertVoucherToCsv(Voucher voucher) {
        return MessageFormat.format("{0},{1},{2}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountValue().getValue());
    }

    public static Voucher convertCsvToVoucher(String voucherInfo) {
        String[] voucherInfoArray = voucherInfo.split(",");
        VoucherType voucherType = VoucherType.getVoucherType(voucherInfoArray[1]);
        return new Voucher(UUID.fromString(voucherInfoArray[0]), voucherType, new DiscountValue(voucherType, voucherInfoArray[2]));
    }

}
