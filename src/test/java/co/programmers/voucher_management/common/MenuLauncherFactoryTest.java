package co.programmers.voucher_management.common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import co.programmers.voucher_management.customer.service.CustomerService;
import co.programmers.voucher_management.exception.MenuTypeMismatchException;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;
import co.programmers.voucher_management.voucher.service.VoucherService;

class MenuLauncherFactoryTest {
	@DisplayName("주어진 메뉴가 아닌 다른 것을 선택시 MenuTypeMismatchException 을 던진다")
	@ParameterizedTest
	@ValueSource(strings = {
			"0",
			"11"
	})
	void invalid_menu_choice(String menu) {
		VoucherService voucherService = mock(VoucherService.class);
		CustomerService customerService = mock(CustomerService.class);
		OutputView outputView = mock(OutputView.class);
		InputView<String> inputView = mock(InputView.class);

		MenuLauncherFactory menuLauncherFactory
				= new MenuLauncherFactory(voucherService, customerService, outputView, inputView);
		assertThrows(MenuTypeMismatchException.class, () -> menuLauncherFactory.of(menu));
	}
}
