package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;

import java.util.List;

public interface VoucherRepository {

    public List<Voucher> list();

    public void add(Voucher voucher);
}
