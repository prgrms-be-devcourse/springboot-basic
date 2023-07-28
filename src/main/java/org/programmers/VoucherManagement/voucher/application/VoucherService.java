package org.programmers.VoucherManagement.voucher.application;

import org.programmers.VoucherManagement.global.response.ErrorCode;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateRequest;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateResponse;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherGetResponses;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherUpdateRequest;
import org.programmers.VoucherManagement.voucher.application.mapper.VoucherServiceMapper;
import org.programmers.VoucherManagement.voucher.domain.DiscountValue;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.domain.VoucherFactory;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherReaderRepository;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherStoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class VoucherService {
    private final VoucherReaderRepository voucherReaderRepository;
    private final VoucherStoreRepository voucherStoreRepository;

    public VoucherService(VoucherReaderRepository voucherReaderRepository, VoucherStoreRepository voucherStoreRepository) {
        this.voucherReaderRepository = voucherReaderRepository;
        this.voucherStoreRepository = voucherStoreRepository;
    }

    @Transactional
    public void updateVoucher(String voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = voucherReaderRepository
                .findById(voucherId)
                .orElseThrow(() -> new VoucherException(ErrorCode.NOT_FOUND_VOUCHER));

        DiscountValue discountValue = new DiscountValue(voucherUpdateRequest.discountValue(), voucher.getDiscountType());

        voucher.changeDiscountValue(discountValue);
        voucherStoreRepository.update(voucher);
    }

    @Transactional
    public VoucherCreateResponse saveVoucher(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = VoucherFactory.createVoucher(voucherCreateRequest);
        return VoucherServiceMapper.INSTANCE.domainToCreateResponse(voucherStoreRepository.insert(voucher));
    }

    public VoucherGetResponses getVoucherList() {
        return new VoucherGetResponses(voucherReaderRepository.findAll());
    }

    @Transactional
    public void deleteVoucher(String voucherId) {
        voucherStoreRepository.delete(voucherId);
    }
}
