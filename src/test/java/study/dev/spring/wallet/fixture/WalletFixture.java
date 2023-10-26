package study.dev.spring.wallet.fixture;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.wallet.domain.Wallet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WalletFixture {

	public static Wallet getWallet() {
		return Wallet.of("1", "2");
	}

	public static List<Wallet> getWallets() {
		return List.of(Wallet.of("1", "2"), Wallet.of("3", "4"));
	}
}
