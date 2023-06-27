package programmers.org.voucher.repository;

import programmers.org.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> getAll();
}
