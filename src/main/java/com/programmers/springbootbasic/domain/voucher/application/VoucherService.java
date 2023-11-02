package com.programmers.springbootbasic.domain.voucher.application;

import static com.programmers.springbootbasic.exception.ErrorCode.FAIL_TO_DELETE_VOUCHER;
import static com.programmers.springbootbasic.exception.ErrorCode.FAIL_TO_UPDATE_VOUCHER;
import static com.programmers.springbootbasic.exception.ErrorCode.NOT_FOUND_VOUCHER;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherIdGenerator;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.UpdateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VoucherService {

    private static final int AFFECTED_ROW_ONE = 1;
    private final VoucherRepository voucherRepository;
    private final VoucherIdGenerator idGenerator;

    public VoucherService(
        VoucherRepository voucherRepository,
        VoucherIdGenerator idGenerator
    ) {
        this.voucherRepository = voucherRepository;
        this.idGenerator = idGenerator;
    }

    @Transactional
    public UUID create(CreateVoucherRequest request) {
        Voucher voucher = request.toEntity(idGenerator.generate());
        voucherRepository.save(voucher);
        return voucher.getId();
    }

    public List<VoucherResponse> findAll() {
        return voucherRepository.findAll().stream()
            .map(VoucherResponse::of)
            .toList();
    }

    public VoucherResponse findById(UUID id) {
        return voucherRepository.findById(id)
            .map(VoucherResponse::of)
            .orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherRepository.findById(id).orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));

        if (voucherRepository.deleteById(id) != AFFECTED_ROW_ONE) {
            throw new VoucherException(FAIL_TO_DELETE_VOUCHER);
        }
    }

    @Transactional
    public UUID update(UUID id, UpdateVoucherRequest request) {
        Voucher exsistedVoucher = voucherRepository.findById(id)
            .orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER));

        var updatedVoucher = new Voucher(id, request.getVoucherType(), request.getBenefitValue());

        if (voucherRepository.update(updatedVoucher) != AFFECTED_ROW_ONE) {
            throw new VoucherException(FAIL_TO_UPDATE_VOUCHER);
        }
        return updatedVoucher.getId();
    }

}
