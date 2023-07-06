package kr.co.springbootweeklymission.voucher.presentation.dto.response;

import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.wallet.domain.entity.Wallet;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoucherResDTO {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {
        private UUID voucherId;
        private int amount;
        private VoucherPolicy voucherPolicy;

        public static VoucherResDTO.READ toVoucherReadDto(Voucher voucher) {
            return VoucherResDTO.READ.builder()
                    .voucherId(voucher.getVoucherId())
                    .amount(voucher.getAmount())
                    .voucherPolicy(voucher.getVoucherPolicy())
                    .build();
        }

        public static VoucherResDTO.READ toVoucherReadDto(Wallet wallet) {
            return VoucherResDTO.READ.builder()
                    .voucherId(wallet.getVoucher().getVoucherId())
                    .amount(wallet.getVoucher().getAmount())
                    .voucherPolicy(wallet.getVoucher().getVoucherPolicy())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FILE {
        private UUID voucherId;
        private int amount;
        private VoucherPolicy voucherPolicy;

        public static VoucherResDTO.FILE toVoucherFile(Voucher voucher) {
            return VoucherResDTO.FILE.builder()
                    .voucherId(voucher.getVoucherId())
                    .amount(voucher.getAmount())
                    .voucherPolicy(voucher.getVoucherPolicy())
                    .build();
        }
    }
}
