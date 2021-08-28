package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.blacklist.Blacklist;
import org.prgms.w3d1.model.voucher.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlacklistRepository {
    Optional<Blacklist> findById(UUID voucherId);
    void save(Blacklist blacklist);
    List<Blacklist> findAll();
}
