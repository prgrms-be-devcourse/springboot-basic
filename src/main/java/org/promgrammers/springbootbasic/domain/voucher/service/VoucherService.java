package org.promgrammers.springbootbasic.domain.voucher.service;

import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.UpdateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.promgrammers.springbootbasic.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.promgrammers.springbootbasic.exception.ErrorCode.NOT_FOUND_VOUCHER;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = VoucherFactory.createVoucher(createVoucherRequest);
        voucherRepository.insert(voucher);
        return new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    @Transactional(readOnly = true)
    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new BusinessException(NOT_FOUND_VOUCHER));

        return new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    @Transactional(readOnly = true)
    public VoucherListResponse findAll() {
        List<Voucher> voucherList = voucherRepository.findAll();

        if (voucherList == null || voucherList.isEmpty()) {
            throw new BusinessException(NOT_FOUND_VOUCHER);
        }

        List<VoucherResponse> voucherResponseList = voucherRepository.findAll()
                .stream()
                .map(voucher -> new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount()))
                .toList();
        return new VoucherListResponse(voucherResponseList);
    }

    @Transactional
    public VoucherResponse update(UpdateVoucherRequest updateVoucherRequest) {
        Voucher voucher = voucherRepository.findById(updateVoucherRequest.voucherId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_VOUCHER));

        if (voucher.isPercentVoucher()) {
            PercentDiscountVoucher percentDiscountVoucher = (PercentDiscountVoucher) voucher;
            percentDiscountVoucher.updateAmount(updateVoucherRequest.discountAmount());
        }

        if (voucher.isFixedAmountVoucher()) {
            FixedAmountVoucher fixedAmountVoucher = (FixedAmountVoucher) voucher;
            fixedAmountVoucher.updateAmount(updateVoucherRequest.discountAmount());
        }
        voucherRepository.update(voucher);

        return new VoucherResponse(updateVoucherRequest.voucherId(), updateVoucherRequest.voucherType(), voucher.getAmount());
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
