package org.prgrms.voucher.dto;

public class VoucherDto {

    public static class RequestDto {

        private final int voucherType;
        private final long discountValue;

        public RequestDto(int voucherType, long discountValue) {

            this.voucherType = voucherType;
            this.discountValue = discountValue;
        }

        public int getVoucherType() {

            return voucherType;
        }

        public long getDiscountValue() {

            return discountValue;
        }
    }

    public static class ResponseDto {

        private final Long voucherId;

        public ResponseDto(Long voucherId) {

            this.voucherId = voucherId;
        }

        public Long getVoucherId() {

            return voucherId;
        }
    }
}
