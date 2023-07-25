package org.programmers.VoucherManagement.voucher.application;

import org.programmers.VoucherManagement.voucher.domain.DiscountValue;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.domain.VoucherFactory;
import org.programmers.VoucherManagement.voucher.dto.request.VoucherCreateRequest;
import org.programmers.VoucherManagement.voucher.dto.request.VoucherUpdateRequest;
import org.programmers.VoucherManagement.voucher.dto.response.VoucherGetResponses;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherReaderRepository;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherStoreRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER;


@Component
@Transactional(readOnly = true)
public class VoucherService {
    private final VoucherReaderRepository voucherReaderRepository;
    private final VoucherStoreRepository voucherStoreRepository;

    public VoucherService(VoucherReaderRepository voucherReaderRepository, VoucherStoreRepository voucherStoreRepository) {
        this.voucherReaderRepository = voucherReaderRepository;
        this.voucherStoreRepository = voucherStoreRepository;
    }

    @Transactional
    public void updateVoucher(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = voucherReaderRepository.findById(voucherId).orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
        DiscountValue discountValue = new DiscountValue(voucherUpdateRequest.discountValue());
        discountValue.validateValue(voucher.getDiscountType());

        voucher.changeDiscountValue(discountValue);
        voucherStoreRepository.update(voucher);
    }

    @Transactional
    public void saveVoucher(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = VoucherFactory.createVoucher(voucherCreateRequest);
        voucherStoreRepository.insert(voucher);
    }

    public VoucherGetResponses getVoucherList() {
        return new VoucherGetResponses(voucherReaderRepository.findAll());
    }

    @Transactional
    public void deleteVoucher(UUID voucherId) {
        voucherStoreRepository.delete(voucherId);
    }
}
