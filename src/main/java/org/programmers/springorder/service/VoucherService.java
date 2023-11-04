package org.programmers.springorder.service;

import org.programmers.springorder.constant.ErrorMessage;
import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;
import org.programmers.springorder.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher createVoucher(VoucherRequestDto voucherDto) {
        Voucher voucher = Voucher.of(voucherDto);
        voucherRepository.insert(voucher);
        log.info("등록된 Voucher => ID: {}, type: {}, value: {}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountValue());
        return voucher;
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> getAllVoucher() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponseDto::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public Voucher findById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.VOUCHER_ID_NOT_EXIST_MESSAGE));
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> findByType(VoucherType voucherType) {
        List<Voucher> vouchers = voucherRepository.findByType(voucherType);
        if (vouchers.isEmpty()) {
            throw new IllegalArgumentException(voucherType.name() + ErrorMessage.VOUCHER_TYPE_NOT_EXIST_MESSAGE);
        }
        return vouchers.stream()
                .map(VoucherResponseDto::of)
                .toList();
    }

    @Transactional
    public void updateVoucher(UUID voucherId, VoucherRequestDto voucherDto) {
        Voucher voucher = this.findById(voucherId);
        voucher.update(voucherDto.getVoucherType(), voucherDto.getDiscountValue());
        voucherRepository.update(voucher);
    }

    @Transactional
    public void deleteVoucher(UUID voucherId) {
        this.findById(voucherId);
        voucherRepository.deleteById(voucherId);
    }

    @Transactional
    public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
        voucherRepository.assignVoucherToCustomer(customerId, voucherId);
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> getVoucherByCustomerId(UUID customerId) {
        return voucherRepository.findByCustomerId(customerId)
                .stream()
                .map(VoucherResponseDto::of)
                .toList();
    }
}
