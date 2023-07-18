package org.programmers.VoucherManagement.voucher.application;

import org.programmers.VoucherManagement.voucher.domain.DiscountValue;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.domain.VoucherFactory;
import org.programmers.VoucherManagement.voucher.dto.request.VoucherCreateRequest;
import org.programmers.VoucherManagement.voucher.dto.request.VoucherUpdateRequest;
import org.programmers.VoucherManagement.voucher.dto.response.VoucherGetResponses;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER;


@Component
@Transactional(readOnly = true)
public class VoucherService {
    private final VoucherRepository repository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.repository = voucherRepository;
    }

    @Transactional
    public void updateVoucher(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = repository.findById(voucherId).orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
        DiscountValue discountValue = new DiscountValue(voucherUpdateRequest.getDiscountValue());
        discountValue.validateValue(voucher.getDiscountType());

        voucher.changeDiscountValue(discountValue);
        repository.update(voucher);
    }

    @Transactional
    public void saveVoucher(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = VoucherFactory.createVoucher(voucherCreateRequest);
        repository.insert(voucher);
    }

    public VoucherGetResponses getVoucherList() {
        return new VoucherGetResponses(repository.findAll());
    }

    @Transactional
    public void deleteVoucher(UUID voucherId) {
        repository.delete(voucherId);
    }
}
