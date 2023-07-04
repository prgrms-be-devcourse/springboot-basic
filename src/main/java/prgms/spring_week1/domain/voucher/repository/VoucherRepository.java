package prgms.spring_week1.domain.voucher.repository;

import prgms.spring_week1.domain.voucher.model.Voucher;

import java.util.List;

public interface VoucherRepository {
    List<Voucher> findAll();

    void insert(Voucher voucher);
}
