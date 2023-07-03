package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.VoucherType;
import com.devcourse.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherService {
    private static final String NEGATIVE_DISCOUNT = "[Error] Discount Value MUST Be Positive. Input : ";
    private static final String OUT_RANGED_DISCOUNT = "[Error] Discount Rate MUST Be Smaller Than 100. Input : ";
    private static final String INVALID_EXPIRATION = "[Error] Expiration Time Cannot Be The Past. Input : ";
    private static final String EXPIRED_VOUCHER = "[Error] This Voucher Is EXPIRED. ExpiredAt : ";
    private static final String USED_VOUCHER = "[Error] This Voucher Is Already USED";
    private static final int MAX_DISCOUNT_RATE = 100;
    private static final int MIN_DISCOUNT = 0;

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(CreateVoucherRequest request) {
        validateRequest(request);
        Voucher voucher = VoucherMapper.toEntity(request);
        voucherRepository.save(voucher);
    }

    public List<GetVoucherResponse> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        return vouchers.stream()
                .map(VoucherMapper::toResponse)
                .toList();
    }

    public void validateRequest(CreateVoucherRequest request) {
        validateDiscount(request);
        validateExpiration(request.expiredAt(), INVALID_EXPIRATION);
    }

    public void validateUsable(Voucher voucher) {
        validateUsed(voucher);
        validateExpiration(voucher.getExpireAt(), EXPIRED_VOUCHER);
    }

    private void validateDiscount(CreateVoucherRequest request) {
        VoucherType voucherType = request.type();
        int discount = request.discount();

        if (isNegative(discount)) {
            throw new IllegalArgumentException(NEGATIVE_DISCOUNT + discount);
        }

        if (voucherType.isPercent() && isRateOutRange(discount)) {
            throw new IllegalArgumentException(OUT_RANGED_DISCOUNT + discount);
        }
    }

    private void validateExpiration(LocalDateTime expiredAt, String message) {
        LocalDateTime now = LocalDateTime.now();

        if (expiredAt.isBefore(now)) {
            throw new IllegalArgumentException(message + expiredAt);
        }
    }

    private void validateUsed(Voucher voucher) {
        if (voucher.isUsed()) {
            throw new IllegalStateException(USED_VOUCHER);
        }
    }

    private boolean isNegative(int discountAmount) {
        return discountAmount <= MIN_DISCOUNT;
    }

    private boolean isRateOutRange(int discountRate) {
        return MAX_DISCOUNT_RATE < discountRate;
    }
}
