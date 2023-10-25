package study.dev.spring.wallet.domain;

import java.util.List;
import java.util.Optional;

public interface WalletRepository {

	Wallet save(Wallet wallet);

	Optional<Wallet> findById(String uuid);

	List<Wallet> findByCustomerId(String customerId);

	List<Wallet> findByVoucherId(String voucherId);

	void deleteByCustomerId(String customerId);
}
