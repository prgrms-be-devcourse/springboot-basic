package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.common.utils.UUIDValueStrategy;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = RuntimeException.class)
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final UUIDValueStrategy uuidValueStrategy;

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = VoucherType.of(voucherRequestDto.getVoucherType(), uuidValueStrategy.generateUUID(), voucherRequestDto.getValue());
        return voucherRepository.save(voucher);
    }

    public Voucher findVoucherById(VoucherRequestDto voucherRequestDto) {
        return voucherRepository.findById(voucherRequestDto.getVoucherId()).orElseThrow(() -> {
            log.warn(ErrorMsg.VOUCHER_NOT_FOUND.getMessage());
            return new RuntimeException(ErrorMsg.VOUCHER_NOT_FOUND.getMessage());
        });
    }

    public void updateVoucher(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = findVoucherById(voucherRequestDto);
        voucher = VoucherType.of(VoucherType.predictVoucherType(voucher), voucherRequestDto.getVoucherId(), voucherRequestDto.getValue());
        voucherRepository.update(voucher);
    }

    public void deleteVoucher(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = findVoucherById(voucherRequestDto);
        voucherRepository.delete(voucher);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }
}
