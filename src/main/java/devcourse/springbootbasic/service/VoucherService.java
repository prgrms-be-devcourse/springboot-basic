package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import devcourse.springbootbasic.repository.voucher.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Transactional
    public Voucher create(VoucherCreateRequest voucherCreateRequest) {
        return voucherRepository.save(voucherCreateRequest.toEntity());
    }

    public List<VoucherFindResponse> findAll() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherFindResponse::new)
                .toList();
    }

    @Transactional
    public Voucher updateDiscountValue(UUID voucherId, long discountValue) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> VoucherException.of(VoucherErrorMessage.NOT_FOUND))
                .updateDiscountValue(discountValue);

        voucher.updateDiscountValue(discountValue);

        if (voucherRepository.update(voucher) == 0) {
            throw VoucherException.of(VoucherErrorMessage.NOT_FOUND);
        }

        return voucher;
    }

    @Transactional
    public Voucher delete(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> VoucherException.of(VoucherErrorMessage.NOT_FOUND));

        if (voucherRepository.delete(voucher) == 0) {
            throw VoucherException.of(VoucherErrorMessage.NOT_FOUND);
        }

        return voucher;
    }
}
