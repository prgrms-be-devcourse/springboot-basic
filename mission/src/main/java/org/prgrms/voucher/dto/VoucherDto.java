package org.prgrms.voucher.dto;

import org.prgrms.voucher.models.VoucherType;

public class VoucherDto {

    public static class CreateVoucherRequest {

        private final VoucherType voucherType;
        private final Long discountValue;

        public CreateVoucherRequest(VoucherType voucherType, Long discountValue) {

            this.voucherType = voucherType;
            this.discountValue = discountValue;
        }

        public VoucherType getVoucherType() {

            return voucherType;
        }

        public Long getDiscountValue() {

            return discountValue;
        }
    }

    public static class CreateVoucherResponse {

        private final Long voucherId;

        public CreateVoucherResponse(Long voucherId) {

            this.voucherId = voucherId;
        }

        public Long getVoucherId() {

            return voucherId;
        }
    }
}
