package org.programers.vouchermanagement.wallet.domain;

import org.programers.vouchermanagement.voucher.exception.NoSuchVoucherException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    Optional<Wallet> findById(UUID id);

    default Wallet getById(UUID id) {
        return findById(id).
                orElseThrow(() -> new NoSuchVoucherException("존재하지 않는 지갑입니다."));
    }

    List<Wallet> findAllByVoucherId(UUID voucherId);

    List<Wallet> findAllByMemberId(UUID memberId);

    List<Wallet> findAll();

    void deleteById(UUID id);
}
