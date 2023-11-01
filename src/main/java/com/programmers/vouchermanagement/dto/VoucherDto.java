package com.programmers.vouchermanagement.dto;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;

import java.time.LocalDateTime;

public class VoucherDto {
    public record CreateRequest(String name, float discountAmount, VoucherType voucherType) {
    }

    public record GetRequest(String voucherId,
                             VoucherType voucherType,
                             LocalDateTime startDate,
                             LocalDateTime endDate) {
    }

    public static class Response {
        private final String id;
        private final String name;
        private final float discountAmount;
        private final LocalDateTime createdAt;
        private final VoucherType voucherType;

        public Response(Voucher voucher) {
            this.id = voucher.getId().toString();
            this.name = voucher.getName();
            this.discountAmount = voucher.getDiscountAmount();
            this.createdAt = voucher.getCreatedAt();
            this.voucherType = voucher.getVoucherType();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public float getDiscountAmount() {
            return discountAmount;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public VoucherType getVoucherType() {
            return voucherType;
        }
    }
}
//public record VoucherDto(UUID id,
//                         String name,
//                         float discountAmount,
//                         LocalDateTime createdAt,
//                         VoucherType voucherType) {
//    public static VoucherDto of(String voucherName, float discountAmount, VoucherType voucherType) {
//        return new VoucherDto(null, voucherName, discountAmount, null, voucherType);
//    }
//
//    public static VoucherDto from(Voucher voucher) {
//        return new VoucherDto(
//                voucher.getId(),
//                voucher.getName(),
//                voucher.getDiscountAmount(),
//                voucher.getCreatedAt(),
//                voucher.getVoucherType());
//    }
//}
