package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.common.utils.UUIDValueStrategy;
import com.programmers.springbootbasic.domain.voucher.dto.VoucherServiceRequestDto;
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

    public Voucher createVoucher(VoucherServiceRequestDto voucherServiceRequestDto) {
        Voucher voucher = VoucherType.of(voucherServiceRequestDto.getVoucherType(), uuidValueStrategy.generateUUID(), voucherServiceRequestDto.getValue());
        return voucherRepository.save(voucher);
    }

    public Voucher findVoucherById(VoucherServiceRequestDto voucherServiceRequestDto) {
        return voucherRepository.findById(voucherServiceRequestDto.getVoucherId()).orElseThrow(() -> {
            log.warn(ErrorMsg.VOUCHER_NOT_FOUND.getMessage());
            return new RuntimeException(ErrorMsg.VOUCHER_NOT_FOUND.getMessage());
        });
    }

    public void updateVoucher(VoucherServiceRequestDto voucherServiceRequestDto) {
        Voucher voucher = findVoucherById(voucherServiceRequestDto);
        voucher = VoucherType.of(VoucherType.predictVoucherType(voucher), voucherServiceRequestDto.getVoucherId(), voucherServiceRequestDto.getValue());
        voucherRepository.update(voucher);
    }

    public void deleteVoucher(VoucherServiceRequestDto voucherServiceRequestDto) {
        Voucher voucher = findVoucherById(voucherServiceRequestDto);
        voucherRepository.delete(voucher);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }
}
