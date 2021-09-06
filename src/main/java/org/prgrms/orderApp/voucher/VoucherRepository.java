package org.prgrms.orderApp.voucher;


import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    int save(Voucher voucher) throws IOException;
    List<Voucher> findAll() throws IOException, ParseException;
}
