package com.programmers.springmission.voucher.application;

import com.programmers.springmission.global.exception.DuplicateException;
import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.NotFoundException;
import com.programmers.springmission.voucher.domain.FixedAmountPolicy;
import com.programmers.springmission.voucher.domain.PercentDiscountPolicy;
import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.presentation.request.VoucherUpdateRequest;
import com.programmers.springmission.voucher.presentation.response.VoucherResponse;
import com.programmers.springmission.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse createVoucher(VoucherCreateRequest voucherCreateRequest) {
        VoucherType voucherType = voucherCreateRequest.getVoucherType();
        Voucher voucher = switch (voucherType) {
            case FIXED_AMOUNT -> new Voucher(new FixedAmountPolicy(voucherType), voucherCreateRequest.getAmount());
            case PERCENT_DISCOUNT -> new Voucher(new PercentDiscountPolicy(voucherType), voucherCreateRequest.getAmount());
        };

        voucherRepository.save(voucher);
        return new VoucherResponse(voucher);
    }

    public VoucherResponse findOneVoucher(UUID voucherId) {
        Voucher voucher = findVoucher(voucherId);

        return new VoucherResponse(voucher);
    }

    public List<VoucherResponse> findByPolicyVoucher(VoucherType voucherType) {
        List<Voucher> voucherList = voucherRepository.findByPolicy(voucherType);
        return voucherList.stream()
                .map(VoucherResponse::new)
                .toList();
    }

    public List<VoucherResponse> findAllVoucher() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return voucherList.stream()
                .map(VoucherResponse::new)
                .toList();
    }

    @Transactional
    public VoucherResponse updateAmount(UUID inputVoucherId, VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = findVoucher(inputVoucherId);

        voucher.updateAmount(voucherUpdateRequest.getAmount());
        voucherRepository.updateAmount(voucher);
        return new VoucherResponse(voucher);
    }

    @Transactional
    public VoucherResponse updateCustomer(UUID inputVoucherId, UUID inputCustomerId) {
        Voucher voucher = findVoucher(inputVoucherId);
        validateAssignedCustomer(voucher);

        voucher.updateCustomer(inputCustomerId);
        voucherRepository.updateCustomer(voucher);
        return new VoucherResponse(voucher);
    }

    @Transactional
    public void deleteVoucher(UUID voucherId) {
        Voucher voucher = findVoucher(voucherId);

        voucherRepository.deleteById(voucher.getVoucherId());
    }

    @Transactional
    public void deleteAllVoucher() {
        voucherRepository.deleteAll();
    }

    private Voucher findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_VOUCHER));
    }

    private void validateAssignedCustomer(Voucher voucher) {
        if (voucher.getCustomerId() != null) {
            throw new DuplicateException(ErrorMessage.DUPLICATE_ASSIGN_VOUCHER);
        }
    }
}
