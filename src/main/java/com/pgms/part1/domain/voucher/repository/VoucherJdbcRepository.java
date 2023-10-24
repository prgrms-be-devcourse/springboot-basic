package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.wallet.entity.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("dev")
@Repository
public class VoucherJdbcRepository implements VoucherRepository{
    @Override
    public List<Voucher> list() {
        return null;
    }

    @Override
    public void add(Voucher voucher) {

    }

    @Override
    public void delete(Voucher voucher) {

    }

    @Override
    public List<Voucher> listVouchers() {
        return null;
    }

    @Override
    public void findVoucherByWallet(Wallet wallet) {

    }
}
