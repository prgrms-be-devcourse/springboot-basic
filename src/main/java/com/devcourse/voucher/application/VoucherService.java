package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherService {
    private static final String NEGATIVE_DISCOUNT = "[Error] Discount Value MUST Be Positive. Input : ";
    private static final String OUT_RANGED_DISCOUNT = "[Error] Discount Rate MUST Be Smaller Than 100. Input : ";
    private static final String INVALID_EXPIRATION = "[Error] Expiration Time Cannot Be The Past. Input : ";
    private static final int MAX_DISCOUNT_RATE = 100;
    private static final int MIN_DISCOUNT = 0;

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(CreateVoucherRequest request) {
        validateRequest(request);
        Voucher voucher = Voucher.of(request.discount(), request.expiredAt(), request.type());
        voucherRepository.save(voucher);
    }

    public List<GetVoucherResponse> findAll() {
        return voucherRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private void validateRequest(CreateVoucherRequest request) {
        validateDiscount(request.type(), request.discount());
        validateExpiration(request.expiredAt());
    }

    private void validateDiscount(Voucher.Type type, int discount) {
        if (isNegative(discount)) {
            throw new IllegalArgumentException(NEGATIVE_DISCOUNT + discount);
        }

        if (type.isPercent() && isRateOutRange(discount)) {
            throw new IllegalArgumentException(OUT_RANGED_DISCOUNT + discount);
        }
    }

    private void validateExpiration(LocalDateTime expiredAt) {
        LocalDateTime now = LocalDateTime.now();

        if (expiredAt.isBefore(now)) {
            throw new IllegalArgumentException(VoucherService.INVALID_EXPIRATION + expiredAt);
        }
    }

    private boolean isNegative(int discountAmount) {
        return discountAmount <= MIN_DISCOUNT;
    }

    private boolean isRateOutRange(int discountRate) {
        return MAX_DISCOUNT_RATE < discountRate;
    }

    private GetVoucherResponse toResponse(Voucher voucher) {
        return new GetVoucherResponse(
                voucher.getId(),
                voucher.getType(),
                voucher.getDiscount(),
                voucher.getExpireAt(),
                voucher.getStatus());
    }
}
