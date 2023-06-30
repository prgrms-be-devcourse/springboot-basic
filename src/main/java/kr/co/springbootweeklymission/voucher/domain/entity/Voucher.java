package kr.co.springbootweeklymission.voucher.domain.entity;

import kr.co.springbootweeklymission.infrastructure.error.exception.WrongVoucherPolicyException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.voucher.api.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import lombok.*;

import java.util.UUID;

@Getter
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
}
