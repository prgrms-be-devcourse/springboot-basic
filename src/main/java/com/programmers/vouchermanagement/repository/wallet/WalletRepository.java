package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;

import java.util.List;

public interface WalletRepository {
    public void save(Wallet wallet);
    public void saveAll(List<Wallet> wallets);
    public List<Wallet> findAll(GetWalletsRequestDto request);
    public void deleteById(int id);
    public void deleteAll();
}
