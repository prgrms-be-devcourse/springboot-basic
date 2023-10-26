package study.dev.spring.app;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.Arrays;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.exception.GlobalException;

@RequiredArgsConstructor
public enum Command {

	EXIT(CommandExecutor::exit),
	BLACK_LIST(CommandExecutor::blackList),
	ALL_CUSTOMER(CommandExecutor::allCustomers),
	CUSTOMERS_BY_VOUCHER(CommandExecutor::customersByVoucher),
	CREATE_VOUCHER(CommandExecutor::createVoucher),
	ALL_VOUCHERS(CommandExecutor::allVouchers),
	VOUCHERS_BY_CUSTOMER(CommandExecutor::vouchersByCustomer),
	ADD_WALLET(CommandExecutor::addWallet),
	REMOVE_WALLET_BY_CUSTOMER(CommandExecutor::removeWalletByCustomer);

	private final Consumer<CommandExecutor> commander;

	public static void execute(CommandExecutor executor, String function) {
		Arrays.stream(values())
			.filter(matcher -> matcher.name().equalsIgnoreCase(function))
			.findFirst()
			.orElseThrow(() -> new GlobalException(UNSUPPORTED_FUNCTION_NAME))
			.commander.accept(executor);
	}
}
