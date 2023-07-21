package com.tangerine.voucher_system.application.voucher.repository;

import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository {

    private final VoucherMap voucherMap;

    public MemoryVoucherRepository() {
        this.voucherMap = new VoucherMap();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return voucherMap.addVoucher(voucher);
    }

    @Override
    public Voucher update(Voucher voucher) {
        return voucherMap.addIfVoucherExist(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.getAllVouchers();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(voucherMap.getVoucherById(voucherId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> findByVoucherType(VoucherType voucherType) {
        return findAll().stream()
                .filter(voucher -> voucher.getVoucherType() == voucherType)
                .findAny();
    }

    @Override
    public Optional<Voucher> findByCreatedAt(LocalDate createdAt) {
        return findAll().stream()
                .filter(voucher -> voucher.getCreatedAt() == createdAt)
                .findAny();
    }

    @Override
    public void deleteById(UUID voucherId) {
        voucherMap.removeVoucherById(voucherId);
    }

}
