package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    List<Voucher> vouchers = new ArrayList<>();

    @Override
    public void saveVoucher(Voucher voucher) {
        vouchers.add(voucher);
    }

}
