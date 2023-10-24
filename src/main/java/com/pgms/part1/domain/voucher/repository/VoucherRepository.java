package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.wallet.entity.Wallet;

import java.util.List;

public interface VoucherRepository {

    public List<Voucher> list();

    public void add(Voucher voucher);

    public void delete(Voucher voucher);

    public List<Voucher> listVouchers();

    public void findVoucherByWallet(Wallet wallet);
}
