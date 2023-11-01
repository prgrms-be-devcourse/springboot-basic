package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.wallet.entity.Wallet;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    public List<Voucher> list();

    public void add(Voucher voucher);

    public void delete(Long id);

    public List<Voucher> findVoucherByWallets(List<Wallet> wallet);

    public Optional<Voucher> findVoucherById(Long id);

    public List<Voucher> findVoucherByFilter(String date, VoucherDiscountType type);
}
