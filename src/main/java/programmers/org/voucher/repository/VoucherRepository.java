package programmers.org.voucher.repository;

import org.springframework.stereotype.Repository;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.dto.VoucherRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> getAll();

    void update(Long id, VoucherRequest request);

    Optional<Voucher> findById(Long id);

    void deleteById(Long id);
}
