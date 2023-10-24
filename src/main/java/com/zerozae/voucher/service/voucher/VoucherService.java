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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> ErrorMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        return VoucherResponse.toDto(voucher);
    }

    public UUID findVoucherOwnerId(UUID voucherId) {
        voucherRepository.findById(voucherId).orElseThrow(() -> ErrorMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        return voucherRepository.findVoucherOwner(voucherId).orElseThrow(() -> ErrorMessage.error("바우처 소유자가 존재하지 않습니다."));
    }

    public List<VoucherResponse> findAllVouchers(){
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponse::toDto)
                .toList();
    }

    public List<VoucherResponse> findHaveVouchers(UUID customerId) {
        List<Voucher> vouchers = voucherRepository.findVouchersByCustomerId(customerId);

        return vouchers.stream()
                .map(VoucherResponse::toDto)
                .toList();
    }

    public void registerVoucher(UUID customerId, UUID voucherId) {
        voucherRepository.findById(voucherId).orElseThrow(() -> ErrorMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        if(isAlreadyRegisterVoucher(voucherId)) {
            throw ErrorMessage.error("이미 다른 회원에게 등록된 바우처입니다.");
        };
        voucherRepository.registerVoucher(customerId, voucherId);
    }

    public void removeVoucherFromCustomer(UUID voucherId) {
        voucherRepository.findById(voucherId).orElseThrow(() -> ErrorMessage.error(VOUCHER_NOT_FOUND_MESSAGE));
        voucherRepository.removeVoucher(voucherId);
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

    private boolean isAlreadyRegisterVoucher(UUID voucherId) {
        Optional<UUID> owner = voucherRepository.findVoucherOwner(voucherId);
        return owner.isPresent();
    }
}