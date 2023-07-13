package com.example.voucher.domain.dto;

import com.example.voucher.domain.Voucher;
import java.util.UUID;

public class VoucherDto {
    private UUID voucherId;
    private double amount;

    private VoucherDto() {
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public double getAmount() {
        return amount;
    }

    public static class Builder {
        private UUID voucherId;
        private double amount;

        public Builder() {
            // 필수 필드를 설정할 수 있는 public 생성자
        }

        public Builder withVoucherId(UUID voucherId) {
            this.voucherId = voucherId;
            return this;
        }

        public Builder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public VoucherDto build() {
            VoucherDto dto = new VoucherDto();
            dto.voucherId = this.voucherId;
            dto.amount = this.amount;
            return dto;
        }
    }

    public Voucher toVoucher() {
        return new Voucher(voucherId, amount);
    }
}
