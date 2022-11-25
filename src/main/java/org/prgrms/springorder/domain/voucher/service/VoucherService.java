package org.prgrms.springorder.domain.voucher.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.repository.VoucherRepository;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher createVoucher(VoucherCreateRequest request) {
        Voucher voucher = VoucherFactory.create(request);
        return voucherRepository.insert(voucher);
    }

    @Transactional(readOnly = true)
    public List<Voucher> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            throw new EntityNotFoundException(Voucher.class, "all");
        }

        return vouchers;
    }

    @Transactional(readOnly = true)
    public List<String> findAllConvertedToString() {
        return findAll().stream()
            .map(Object::toString)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteVoucherById(UUID voucherId) {
        findById(voucherId);
        voucherRepository.deleteById(voucherId);
    }

    @Transactional
    public Voucher findById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
            .orElseThrow(() -> new EntityNotFoundException(Voucher.class, voucherId));
    }

    @Transactional(readOnly = true)
    public List<Voucher> findAllBy(LocalDateTime startDate, LocalDateTime endDate, VoucherType voucherType) {
        return voucherRepository.findAllBy(startDate, endDate, voucherType);
    }

    @Transactional(readOnly = true)
    public boolean existsVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).isPresent();
    }

}