package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.domain.VoucherType;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Voucher update(Voucher voucher) {
        try {
            return voucherRepository.update(voucher);
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage());
        }
    }

    public void delete(UUID voucherId) {
        try {
            voucherRepository.delete(voucherId);
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage());
        }
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher findById(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElse(null);
    }

    public void exit() {
        System.exit(0);
    }

    public List<Voucher> findBetweenDate(LocalDateTime begin, LocalDateTime end) {
        return voucherRepository.findBetweenDate(begin, end);
    }

    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return voucherRepository.findByType(voucherType);
    }
}
