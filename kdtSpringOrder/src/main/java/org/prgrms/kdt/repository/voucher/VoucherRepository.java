package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//jdbc 를 이용할 수도 있고, 관계형데이터베이스를 이용할 수도 있고, 비관계형데이터베이스를 이용할 수도 있다.
//즉 데이터를 보관하는 방식이 계속 바뀔 수 있기때문에 대부분 Repository는 인터페이스를 도출하게 된다.
public interface VoucherRepository {

    Optional<Voucher> save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByVoucherId(UUID customerId);

    List<Voucher> findByVoucherType(VoucherType voucherType);

    List<Voucher> findByVoucherTerm(LocalDateTime beforeDate, LocalDateTime afterDate);

    List<Voucher> findAll();

    void deleteVoucher(UUID voucherId, UUID customerId);
}