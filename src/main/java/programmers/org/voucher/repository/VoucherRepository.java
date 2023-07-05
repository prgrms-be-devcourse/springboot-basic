package programmers.org.voucher.repository;

import org.springframework.stereotype.Repository;
import programmers.org.voucher.domain.Voucher;

import java.util.List;

@Repository
public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> getAll();
}
