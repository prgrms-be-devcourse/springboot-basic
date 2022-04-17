package org.prgms.management.wallet.repository;

import org.prgms.management.wallet.entity.Wallet;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO : CRUD 구현
public interface WalletRepository {
    Map<UUID, Wallet> getAll();

    // TODO : 매개변수로 Optional 객체를 사용하는게 좋을까?
    Optional<Wallet> insert(Wallet wallet);
}
