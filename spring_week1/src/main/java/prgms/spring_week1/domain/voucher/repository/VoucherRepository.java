package prgms.spring_week1.domain.voucher.repository;

import prgms.spring_week1.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<List<Voucher>> findAll();
    Voucher insert(Voucher voucher);
}
