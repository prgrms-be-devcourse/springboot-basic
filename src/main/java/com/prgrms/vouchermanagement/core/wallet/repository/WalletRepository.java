package com.prgrms.vouchermanagement.core.wallet.repository;

import com.prgrms.vouchermanagement.core.wallet.domain.Wallet;

import java.util.List;

public interface WalletRepository {

    /**
     * 저장
     *
     * @param wallet
     * @return
     */
    Wallet save(Wallet wallet);

    /**
     * 전체 조회
     *
     * @return List<Wallet>
     */
    List<Wallet> findAll();

    /**
     * 전체 삭제
     *
     */
    void deleteAll();

    /**
     * 고객 id에 해당하는 모든 지갑 조회
     *
     * @param customerId
     * @return
     */
    List<Wallet> findByCustomerId(String customerId);

    /**
     * 특정 고객이 보유한 바우처들 삭제
     *
     * @param customerId
     */
    void deleteAllByCustomerId(String customerId);

    /**
     * 해당 바우처 id를 가진 모든 지갑 조회
     *
     * @param voucherId
     * @return
     */
    List<Wallet> findByVoucherId(String voucherId);

}
