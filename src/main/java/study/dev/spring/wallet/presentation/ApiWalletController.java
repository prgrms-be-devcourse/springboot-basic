package study.dev.spring.wallet.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.dev.spring.wallet.application.WalletService;
import study.dev.spring.wallet.application.dto.CreateWalletRequest;
import study.dev.spring.wallet.application.dto.DeleteWalletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
public class ApiWalletController {

	private final WalletService walletService;

	@PostMapping
	public ResponseEntity<Void> addWallet(
		@RequestBody CreateWalletRequest request
	) {
		String walletId = walletService.addWallet(request.customerId(), request.voucherId());

		return ResponseEntity
			.created(URI.create(String.format("/api/wallets/%s", walletId)))
			.build();
	}

	@DeleteMapping
	public ResponseEntity<Void> removeWalletByCustomer(
		@RequestBody DeleteWalletRequest request
	) {
		walletService.deleteByCustomer(request.customerId());

		return ResponseEntity.noContent().build();
	}
}
