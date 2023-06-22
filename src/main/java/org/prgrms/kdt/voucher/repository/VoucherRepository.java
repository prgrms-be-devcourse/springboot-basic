package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public interface VoucherRepository {

    public Voucher insert(Voucher voucher) throws IOException;
    public Optional<Voucher> findById(UUID voucherId);
    public List<Voucher> findAll() throws IOException, ClassNotFoundException;
}
