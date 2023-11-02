package com.zerozae.voucher.service.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherCondition;
import com.zerozae.voucher.dto.voucher.VoucherCreateRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import com.zerozae.voucher.repository.voucher.VoucherRepository;
import com.zerozae.voucher.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VoucherService {

    private static final String VOUCHER_NOT_FOUND_MESSAGE = "바우처가 존재하지 않습니다.";

    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;

    public VoucherService(VoucherRepository voucherRepository, WalletRepository walletRepository) {
        this.voucherRepository = voucherRepository;
        this.walletRepository = walletRepository;
    }

    public VoucherResponse createVoucher(VoucherCreateRequest voucherRequest) {
        VoucherType voucherType = VoucherType.of(voucherRequest.voucherType());
        try {
            Voucher voucher = switch (voucherType) {
                case FIXED -> new FixedDiscountVoucher(voucherRequest.discount());
                case PERCENT ->  new PercentDiscountVoucher(voucherRequest.discount());
            };
            voucherRepository.save(voucher);
            return VoucherResponse.toDto(voucher);
        }catch (ExceptionMessage e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> findAllVouchers() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponse::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> ExceptionMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        return VoucherResponse.toDto(voucher);
    }

    public VoucherResponse update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> ExceptionMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        voucherRepository.update(voucherId, voucherUpdateRequest);
        return VoucherResponse.toDto(voucher);
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.findById(voucherId).orElseThrow(() -> ExceptionMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        voucherRepository.deleteById(voucherId);
        walletRepository.deleteByVoucherId(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> findVoucherByCondition(VoucherCondition condition) {
        List<Voucher> vouchers = voucherRepository.findVoucherByCondition(condition);
        return vouchers.stream().map(VoucherResponse::toDto).toList();
    }
}
