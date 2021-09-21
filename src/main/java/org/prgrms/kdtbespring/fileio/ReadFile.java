package org.prgrms.kdtbespring.fileio;

import org.prgrms.kdtbespring.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadFile {
    List<Voucher> readFile();
    Optional<Voucher> readRecord(UUID voucherId);
}
