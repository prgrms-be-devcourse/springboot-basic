package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final List<Voucher> voucherList = new ArrayList<>();

    @Override
    public List<Voucher> findAll() {
        return voucherList;
    }

    @Override
    public void save(Voucher voucher) {
        voucherList.add(voucher);
    }
}
