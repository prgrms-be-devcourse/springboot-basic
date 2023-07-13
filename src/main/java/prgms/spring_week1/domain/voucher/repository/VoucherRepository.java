package prgms.spring_week1.domain.voucher.repository;

import org.springframework.stereotype.Repository;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    List<Voucher> findAll();

    void insert(Voucher voucher);

    default List<Voucher> findByType(String voucherType) { return null; };

    default void delete(VoucherType voucherType) {};
}
