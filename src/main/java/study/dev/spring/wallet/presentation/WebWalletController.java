package study.dev.spring.wallet.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import study.dev.spring.wallet.application.WalletService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WebWalletController {

	private final WalletService walletService;

	@GetMapping("/new")
	public String getNewWalletPage() {
		return "wallets/new_wallet";
	}

	@GetMapping("/remove")
	public String getRemoveWalletPage() {
		return "wallets/remove_wallet";
	}

	@PostMapping
	public String addWallet(
		@RequestParam String customerId,
		@RequestParam String voucherId
	) {
		walletService.addWallet(customerId, voucherId);

		return "redirect:/vouchers";
	}

	@PostMapping("/remove")
	public String removeWalletByCustomer(
		@RequestParam String customerId
	) {
		walletService.deleteByCustomer(customerId);

		return "redirect:/vouchers";
	}
}
