package org.programmers.springbootbasic.voucher.service;

import org.programmers.springbootbasic.exception.DuplicateObjectKeyException;
import org.programmers.springbootbasic.exception.NotUpdateException;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long value, LocalDateTime createdAt) {
        if (checkVoucherExist(voucherId)) {
            throw new DuplicateObjectKeyException("이미 존재하는 바우처 입니다.");
        }
        var voucher = VoucherType.findByType(String.valueOf(voucherType))
                .create(voucherId, value, createdAt);
        return voucherRepository.insert(voucher);
    }

    public Voucher updateVoucher(UUID voucherId, long value) {
        if (!checkVoucherExist(voucherId)) {
            throw new NotUpdateException("업데이트 할 바우처가 없습니다.");
        }
        Voucher voucher = getVoucher(voucherId).orElseThrow();
        return voucherRepository.update(voucher.changeValue(value));
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    private boolean checkVoucherExist(UUID voucherId) {
        return voucherRepository.getCountByVoucherId(voucherId) > 0;
    }
}
