package com.zerozae.voucher.service.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.repository.voucher.VoucherRepository;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private static final String VOUCHER_NOT_FOUND_MESSAGE = "바우처가 존재하지 않습니다.";
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse createVoucher(VoucherRequest voucherRequest) {
        VoucherType voucherType = voucherRequest.getVoucherType();
        try {
            Voucher voucher = switch (voucherType) {
                case FIXED -> new FixedDiscountVoucher(voucherRequest.getDiscount());
                case PERCENT ->  new PercentDiscountVoucher(voucherRequest.getDiscount());
            };
            voucherRepository.save(voucher);
            return VoucherResponse.toDto(voucher);
        }catch (ErrorMessage e) {
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public List<VoucherResponse> findAllVouchers() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponse::toDto)
                .toList();
    }

    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> ErrorMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        return VoucherResponse.toDto(voucher);
    }

    public void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        voucherRepository.findById(voucherId).orElseThrow(() -> ErrorMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        voucherRepository.update(voucherId, voucherUpdateRequest);
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.findById(voucherId).orElseThrow(() -> ErrorMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        voucherRepository.deleteById(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
