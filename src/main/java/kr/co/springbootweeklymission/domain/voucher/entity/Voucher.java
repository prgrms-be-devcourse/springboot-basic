package kr.co.springbootweeklymission.domain.voucher.entity;

import kr.co.springbootweeklymission.domain.voucher.api.request.VoucherReqDTO;
import kr.co.springbootweeklymission.domain.voucher.api.response.VoucherResDTO;
import kr.co.springbootweeklymission.domain.voucher.entity.model.VoucherPolicy;
import kr.co.springbootweeklymission.domain.voucher.exception.WrongVoucherPolicyException;
import kr.co.springbootweeklymission.global.error.model.ResponseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Voucher {
    private UUID voucherId;
    private int amount;
    private VoucherPolicy voucherPolicy;

    @Builder
    private Voucher(UUID voucherId,
                    int amount,
                    VoucherPolicy voucherPolicy) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherPolicy = voucherPolicy;
    }

    public static Voucher toVoucher(VoucherReqDTO.CREATE create) {
        return Voucher.builder()
                .voucherId(UUID.randomUUID())
                .amount(create.getAmount())
                .voucherPolicy(create.getVoucherPolicy())
                .build();
    }

    public static VoucherResDTO.READ toVoucherReadDto(Voucher voucher) {
        return VoucherResDTO.READ.builder()
                .voucherId(voucher.voucherId)
                .amount(voucher.amount)
                .voucherPolicy(voucher.voucherPolicy)
                .build();
    }

    public static VoucherResDTO.FILE toVoucherFile(Voucher voucher) {
        return VoucherResDTO.FILE.builder()
                .voucherId(voucher.voucherId)
                .amount(voucher.amount)
                .voucherPolicy(voucher.voucherPolicy)
                .build();
    }

    public int discount(int price) {
        if (price < this.amount) {
            throw new WrongVoucherPolicyException(ResponseStatus.FAIL_WRONG_DISCOUNT);
        }

        return this.voucherPolicy
                .getDiscount()
                .apply(price, this.amount);
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
