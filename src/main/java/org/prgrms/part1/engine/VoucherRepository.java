package org.prgrms.part1.engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
}
