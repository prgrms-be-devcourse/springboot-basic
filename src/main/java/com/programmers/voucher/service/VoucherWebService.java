package com.programmers.voucher.service;

import com.programmers.voucher.domain.VoucherEntity;
import com.programmers.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherWebService implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherWebService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void createVoucher(String type, int discount) {
        VoucherEntity voucherEntity = new VoucherEntity(UUID.randomUUID(), type, discount, LocalDateTime.now(), LocalDateTime.now().plusMonths(6));
        voucherRepository.insert(voucherEntity);
    }

    @Override
    public List<VoucherEntity> getAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public List<VoucherEntity> findByType(String type) {
        return voucherRepository.findByType(type);
    }

    @Override
    public Optional<VoucherEntity> findById(UUID id) {
        return voucherRepository.findByID(id);
    }

    @Override
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
