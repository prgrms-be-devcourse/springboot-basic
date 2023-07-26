package com.programmers.voucher.domain.voucher.service;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import com.programmers.voucher.domain.voucher.util.VoucherErrorMessages;
import com.programmers.voucher.domain.voucher.util.VoucherMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.programmers.voucher.domain.voucher.util.VoucherMessages.CREATED_NEW_VOUCHER;

@Service
public class VoucherService {
    private static final Logger LOG = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public UUID createVoucher(VoucherType voucherType, long amount) {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = voucherType.createVoucher(voucherId, amount);
        voucherRepository.save(voucher);

        LOG.info(CREATED_NEW_VOUCHER, voucher.toString());
        return voucherId;
    }

    @Transactional(readOnly = true)
    public List<VoucherDto> findVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VoucherDto> findVouchers(VoucherType voucherType,
                                         LocalDateTime startTime, LocalDateTime endTime) {
        List<Voucher> vouchers = voucherRepository.findAll(voucherType, startTime, endTime);
        return vouchers.stream()
                .map(VoucherDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public VoucherDto findVoucher(UUID voucherId) {
        Voucher voucher = findVoucherOrElseThrow(voucherId);

        return VoucherDto.from(voucher);
    }

    @Transactional
    public void deleteVoucher(UUID voucherId) {
        Voucher voucher = findVoucherOrElseThrow(voucherId);

        voucherRepository.deleteById(voucherId);
        LOG.info(VoucherMessages.DELETE_VOUCHER, voucher);
    }

    private Voucher findVoucherOrElseThrow(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> {
                    String errorMessage = MessageFormat.format(VoucherErrorMessages.NO_SUCH_VOUCHER, voucherId);
                    LOG.warn(errorMessage);
                    return new NoSuchElementException(errorMessage);
                });
        return voucher;
    }
}
