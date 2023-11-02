package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.common.utils.LocalDateValueStrategy;
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
    private final LocalDateValueStrategy localDateValueStrategy;

    @Transactional(readOnly = true)
    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherServiceRequestDto voucherServiceRequestDto) {
        Voucher voucher = VoucherType.of(voucherServiceRequestDto.getVoucherType(),
                uuidValueStrategy.generateUUID(),
                voucherServiceRequestDto.getValue(),
                localDateValueStrategy.generateLocalDate());
        return voucherRepository.save(voucher);
    }

    @Transactional(readOnly = true)
    public Voucher findVoucherById(VoucherServiceRequestDto voucherServiceRequestDto) {
        return voucherRepository.findById(voucherServiceRequestDto.getVoucherId()).orElseThrow(() -> {
            log.warn(ErrorMsg.VOUCHER_NOT_FOUND.getMessage());
            return new RuntimeException(ErrorMsg.VOUCHER_NOT_FOUND.getMessage());
        });
    }

    @Transactional(readOnly = true)
    public List<Voucher> findVouchersByType(VoucherServiceRequestDto voucherServiceRequestDto) {
        if (!VoucherType.predictVoucherTypeNumber(voucherServiceRequestDto.getVoucherType())) {
            throw new IllegalArgumentException(ErrorMsg.WRONG_VOUCHER_TYPE_NUMBER.getMessage());
        }
        return voucherRepository.findByVoucherType(voucherServiceRequestDto.getVoucherType());
    }

    @Transactional(readOnly = true)
    public List<Voucher> findVouchersByDate(VoucherServiceRequestDto voucherServiceRequestDto) {
        return voucherRepository.findByDate(voucherServiceRequestDto.getDate());
    }

    public void updateVoucher(VoucherServiceRequestDto voucherServiceRequestDto) {
        Voucher voucher = findVoucherById(voucherServiceRequestDto);
        voucher = VoucherType.of(VoucherType.predictVoucherType(voucher),
                voucherServiceRequestDto.getVoucherId(),
                voucherServiceRequestDto.getValue(),
                voucher.getCreatedAt());
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
