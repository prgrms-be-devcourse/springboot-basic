package org.prgrms.orderApp.voucher;


import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    int insert(Voucher voucher);
    List<Voucher> findAll();
    int deleteById(UUID voucherId);
    List<Voucher> findAllByType(String voucherType);
    List<Voucher> findAllByCreatedDate(LocalDateTime fromDate, LocalDateTime toDate);
}
