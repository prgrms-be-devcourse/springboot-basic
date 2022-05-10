package com.dojinyou.devcourse.voucherapplication.voucher.domain;

import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.VoucherEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class VoucherMapper {
    public static final int indexId = 0;
    public static final int indexType = 1;
    public static final int indexAmount = 2;

    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    public static final String ERROR_MESSAGE_FOR_FILE_CONVERT = "FILE 데이터 변환에 실패하였습니다.";

    public static Voucher requestDtoToDomain(VoucherRequest dto) {
        nullCheck(dto);
        VoucherType type = dto.getType();
        VoucherAmount amount = dto.getAmount();

        nullCheck(type, amount);
        return Voucher.of(type, amount);
    }

    public static VoucherResponse domainToResponseDto(Voucher voucher) {
        nullCheck(voucher);
        Long id = voucher.getId();
        VoucherType type = voucher.getType();
        VoucherAmount amount = voucher.getAmount();
        LocalDateTime createdAt = voucher.getCreatedAt();
        LocalDateTime updatedAt = voucher.getUpdatedAt();

        nullCheck(id, type, amount, createdAt, updatedAt);
        return new VoucherResponse(id, type, amount, createdAt, updatedAt);

    }

    public static VoucherEntity domainToEntity(Long id, Voucher voucher) {
        nullCheck(id, voucher);
        VoucherType type = voucher.getType();
        VoucherAmount amount = voucher.getAmount();
        return VoucherEntity.of(id, type, amount, null, null);
    }

    public static Voucher entityToDomain(VoucherEntity voucherEntity) {
        nullCheck(voucherEntity);
        Long id = voucherEntity.getId();
        VoucherType type = voucherEntity.getVoucherType();
        VoucherAmount amount = voucherEntity.getVoucherAmount();
        LocalDateTime createdAt = voucherEntity.getCreatedAt();
        LocalDateTime updatedAt = voucherEntity.getUpdatedAt();

        return Voucher.of(id, type, amount, createdAt, updatedAt);
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
        //TODO: try ~ catch 구문 관련 refacting 때 처리
        try {
            Long id = Long.valueOf(fileFormatValues[indexId]);
            VoucherType type = VoucherType.from(fileFormatValues[indexType]);
            VoucherAmount amount = VoucherAmount.of(type, Integer.parseInt(fileFormatValues[indexAmount]));
            return VoucherEntity.of(id, type, amount,null,null);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_FILE_CONVERT);
        }
    }

    public static Voucher resultSetToDomain(ResultSet rs) {
        try {
            Long id = rs.getLong(1);
            VoucherType type = VoucherType.valueOf(rs.getString("type"));
            VoucherAmount amount = VoucherAmount.of(type, rs.getInt("amount"));
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

            return Voucher.of(id, type, amount, createdAt, updatedAt);
        } catch (SQLException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

    }

    private static void nullCheck(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
            }
        }
    }
}
