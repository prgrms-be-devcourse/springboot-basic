package devcourse.springbootbasic.repository.voucher;


import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAllWithSearchConditions(VoucherType voucherType, LocalDate startDate, LocalDate endDate);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    boolean update(Voucher voucher);

    void delete(Voucher voucher);

    List<Voucher> findByCustomerId(UUID customerId);
}
