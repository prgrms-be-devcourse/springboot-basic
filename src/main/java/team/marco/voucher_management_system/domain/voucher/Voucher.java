package team.marco.voucher_management_system.domain.voucher;

import java.util.UUID;

import static java.text.MessageFormat.format;

public class Voucher {
    private static final int MIN_AMOUNT = 1_000;
    private static final int MAX_AMOUNT = 100_000;
    private static final int MIN_PERCENT = 5;
    private static final int MAX_PERCENT = 100;

    private final Long id;
    private final UUID code;
    private final VoucherType voucherType;
    private final int discountValue;
    private String name;

    private Voucher(Long id, UUID code, VoucherType voucherType, int discountValue, String name) {
        this.id = id;
        this.code = code;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public UUID getCode() {
        return code;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private Long id;
        private VoucherType voucherType;
        private int discountValue;
        private UUID code = UUID.randomUUID();
        private String name;

        public Builder(Long id, VoucherType voucherType, int discountValue) {
            this.id = id;
            this.voucherType = voucherType;
            this.discountValue = discountValue;
            switch (voucherType) {
                case FIXED -> name = format("{0}원 할인 쿠폰", discountValue);
                case PERCENT -> name = format("{0}% 할인 쿠폰", discountValue);
            }
        }

        public Builder code(UUID code) {
            this.code = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Voucher build() {
            switch (voucherType) {
                case FIXED -> validateAmount(discountValue);
                case PERCENT -> validatePercent(discountValue);
            }
            return new Voucher(id, code, voucherType, discountValue, name);
        }

        private void validateAmount(int amount) {
            if(amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
                throw new IllegalArgumentException(
                        format("할인 금액은 {0}원 이상, {1}원 이하이어야 합니다.", MIN_AMOUNT, MAX_AMOUNT)
                );
            }
        }

        private void validatePercent(int percent) {
            if(percent < MIN_PERCENT || percent > MAX_PERCENT) {
                throw new IllegalArgumentException(
                        format("할인 비율은 {0}% 이상, {1}% 이하이어야 합니다.", MIN_PERCENT, MAX_PERCENT)
                );
            }
        }
    }
}
