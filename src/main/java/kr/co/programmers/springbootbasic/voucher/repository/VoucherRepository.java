package kr.co.programmers.springbootbasic.voucher.repository;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    Optional<Voucher> findVoucherById(UUID voucherId);

    List<Voucher> listAll();

    void deleteById(UUID voucherId);
}
