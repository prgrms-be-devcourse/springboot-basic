package prgms.spring_week1.domain.voucher.repository;

import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.util.List;
import java.util.Map;

public interface VoucherRepository {
    List<Voucher> findAll();

    void insert(Voucher voucher);
}
