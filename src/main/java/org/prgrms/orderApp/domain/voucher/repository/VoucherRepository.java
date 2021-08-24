package org.prgrms.orderApp.domain.voucher.repository;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.domain.voucher.model.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    int save(Voucher voucher) throws IOException;
    List<Voucher> findAll() throws IOException, ParseException;
}
