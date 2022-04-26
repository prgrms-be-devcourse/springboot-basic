package org.prgrms.vouchermanager.infrastructure.dto;

import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public final class VoucherDto {

    public static class VoucherListResponse {
        private final List<VoucherData> vouchers;

        public VoucherListResponse(List<Voucher> vouchers) {
            this.vouchers = vouchers.stream().map(VoucherData::new).toList();
        }

        @Override
        public String toString() {
            return this.vouchers.stream()
                    .map(VoucherData::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static class VoucherData {
        private final String id;
        private final String type;
        private final long amount;

        public VoucherData(Voucher voucher) {
            this.id = voucher.getId().toString();
            this.type = voucher.getType().name();
            this.amount = voucher.getDiscountValue();
        }

        @Override
        public String toString() {
            return """
                    id: {id}, type: {type}, amount: {amount}"""
                    .replace("{id}", id)
                    .replace("{type}", type)
                    .replace("{amount}", String.valueOf(amount));
        }
    }
}
