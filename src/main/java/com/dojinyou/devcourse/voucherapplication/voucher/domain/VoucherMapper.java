package com.dojinyou.devcourse.voucherapplication.voucher.domain;

import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.FixedAmountVoucherEntity;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.PercentAmountVoucherEntity;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.VoucherEntity;

public class VoucherMapper {
    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    public static final int indexId = 0;
    public static final int indexType = 1;
    public static final int indexAmount = 2;

    public static Voucher getDomain(Long id, VoucherType type, VoucherAmount amount) {
        switch (type) {
            case FIXED:
                return new FixedAmountVoucher(id, type, (FixedAmount) amount);
            case PERCENT:
                return new PercentAmountVoucher(id, type, (PercentAmount) amount);
        }
        throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
    }

    public static VoucherEntity getEntity(Long id, VoucherType type, VoucherAmount amount) {
        switch (type) {
            case FIXED:
                return new FixedAmountVoucherEntity(id, type, amount);
            case PERCENT:
                return new PercentAmountVoucherEntity(id, type, amount);
        }
        throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
    }

    private static VoucherResponse getResponseDto(Long id, VoucherType type, VoucherAmount amount) {
        return new VoucherResponse(id, type, amount);
    }

    public static Voucher requestDtoToDomain(VoucherRequest dto) {
        nullCheck(dto);
        VoucherType type = dto.getVoucherType();
        VoucherAmount amount = dto.getVoucherAmount();

        nullCheck(type, amount);
        return getDomain(null, type, amount);
    }

    public static VoucherResponse domainToResponseDto(Voucher voucher) {
        nullCheck(voucher);
        Long id = voucher.getVoucherId();
        VoucherType type = voucher.getVoucherType();
        VoucherAmount amount = voucher.getVoucherAmount();

        nullCheck(id, type, amount);
        return getResponseDto(id, type, amount);

    }

    public static VoucherEntity domainToEntity(Long id, Voucher voucher) {
        nullCheck(id, voucher);
        VoucherType type = voucher.getVoucherType();
        VoucherAmount amount = voucher.getVoucherAmount();
        return getEntity(id, type, amount);
    }

    public static Voucher entityToDomain(VoucherEntity voucherEntity) {
        nullCheck(voucherEntity);
        Long id = voucherEntity.getId();
        VoucherType type = voucherEntity.getVoucherType();
        VoucherAmount amount = voucherEntity.getVoucherAmount();

        return getDomain(id, type, amount);
    }

    public static String[] entityToFileFormat(VoucherEntity voucherEntity) {
        if (voucherEntity == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        String stringId = String.valueOf(voucherEntity.getId());
        String stringType = String.valueOf(voucherEntity.getVoucherType());
        String stringAmount = String.valueOf(voucherEntity.getAmount());
        return new String[] {stringId, stringType, stringAmount};
    }

    public static VoucherEntity fileFormatToEntity(String[] fileFormatValues) {
        if (fileFormatValues == null || fileFormatValues.length != 3) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        Long id = Long.valueOf(fileFormatValues[indexId]);
        VoucherType type = VoucherType.from(fileFormatValues[indexType]);
        VoucherAmount amount = VoucherAmount.of(type, Integer.parseInt(fileFormatValues[indexAmount]));
        return VoucherMapper.getEntity(id, type, amount);
    }

    private static void nullCheck(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
            }
        }
    }
}
