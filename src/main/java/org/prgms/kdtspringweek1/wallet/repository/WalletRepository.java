package org.prgms.kdtspringweek1.wallet.repository;

import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.wallet.entity.Wallet;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;

import java.util.List;
import java.util.UUID;

public interface WalletRepository { // 실제세계를 먼저 생각해보면 요구사항 추가 시 좀 더 유연하게 수정 가능하다. 에) wallet(고객 정보, 바우처 리스트), db는 나중에 고려해보기

    Wallet save(Wallet wallet);

    List<Voucher> findAllVouchersByCustomerId(UUID customerId);

    void deleteByVoucherIdAndCustomerId(UUID voucherId, UUID customerId);

    List<Customer> findAllCustomersByVoucherId(UUID voucherId);
}
