package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherUpdateForm;
import com.blessing333.springbasic.voucher.repository.VoucherRepository;
import com.blessing333.springbasic.voucher.service.exception.VoucherDeleteFailException;
import com.blessing333.springbasic.voucher.service.exception.VoucherFindFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DefaultVoucherService implements VoucherService {
    private final VoucherRepository repository;

    @Transactional
    @Override
    public Voucher registerVoucher(VoucherCreateForm form) {
        UUID id = UUID.randomUUID();
        Voucher newVoucher = new Voucher(id, form.getVoucherType(), form.getDiscountAmount());
        repository.insert(newVoucher);
        return newVoucher;
    }

    @Override
    public List<Voucher> loadAllVoucher() {
        return repository.findAll();
    }

    @Override
    public Voucher loadVoucherById(UUID voucherId) {
        return repository.findById(voucherId).orElseThrow(VoucherFindFailException::new);
    }

    @Override
    public List<Voucher> loadVouchersByType(Voucher.VoucherType type) {
        return repository.findByVoucherType(type);
    }

    @Transactional
    @Override
    public void deleteVoucher(UUID voucherId) {
        try {
            repository.deleteById(voucherId);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new VoucherDeleteFailException("존재하지 않는 바우처 삭제 시도.",e);
        }
    }

    @Transactional
    @Override
    public void updateVoucher(VoucherUpdateForm form) {
        Voucher target = repository.findById(form.getVoucherId())
                .orElseThrow(VoucherFindFailException::new);
        target.changeVoucherType(form.getVoucherType());
        target.changeDiscountAmount(form.getDiscountAmount());
        repository.update(target);
    }
}
