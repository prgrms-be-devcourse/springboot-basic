package prgms.spring_week1.domain.voucher.repository;

import prgms.spring_week1.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    List<Voucher> findAll();
    Voucher insert(Voucher voucher);
}
