package com.programmers.commandLine.domain.voucher.repository.impl;

import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {

    private  Map<UUID, Voucher> voucherMap = new LinkedHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Optional<Voucher> findVoucher = Optional.ofNullable(voucherMap.get(voucherId));
        return findVoucher;
    }

    @Override
    public Voucher create(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new LinkedList<>();
        for (UUID key : voucherMap.keySet()) {
            voucherList.add(voucherMap.get(key));
        }

        return voucherList;
    }
}
