package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.entity.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId) throws IOException;

    Voucher insert(Voucher voucher) throws IOException;

    List<Voucher> findAll() throws IOException;
}
