package kr.co.programmers.springbootbasic.voucher.dto;

import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherResponse {
    private static final String FIXED_VOUCHER_FORMAT = """
            종류 : {0}
            아이디 : {1}
            할인 가격 : {2}원
            생성시기 : {3}
                 
            """;
    private static final String PERCENT_VOUCHER_FORMAT = """
            종류 : {0}
            아이디 : {1}
            할인 가격 : {2}%
            생성시기 : {3}
                        
            """;

    private final VoucherType type;
    private final UUID voucherId;
    private final Long amount;
    private final LocalDateTime createdAt;
    private final UUID walletId;

    public VoucherResponse(UUID voucherId) {
        this.type = null;
        this.voucherId = voucherId;
        this.amount = null;
        this.createdAt = null;
        this.walletId = null;
    }

    private VoucherResponse(VoucherType type, UUID voucherId, Long amount, LocalDateTime createdAt, UUID walletId) {
        this.type = type;
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.walletId = walletId;
    }

    public VoucherType getType() {
        return type;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public static VoucherResponse of(Voucher voucher) {
        VoucherType type = voucher.getType();
        UUID voucherId = voucher.getId();
        long amount = voucher.getAmount();
        LocalDateTime createdAt = voucher.getCreatedAt();
        UUID walletId = voucher.getWalletId();

        return new VoucherResponse(type, voucherId, amount, createdAt, walletId);
    }

    public String formatMessage() {
        if (this.type == VoucherType.FIXED_AMOUNT) {
            return MessageFormat.format(FIXED_VOUCHER_FORMAT,
                    this.type,
                    this.voucherId,
                    this.amount,
                    this.createdAt);
        }

        return MessageFormat.format(PERCENT_VOUCHER_FORMAT,
                this.type,
                this.voucherId,
                this.amount,
                this.createdAt);
    }
}
