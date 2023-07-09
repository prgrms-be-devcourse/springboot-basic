package co.programmers.voucher_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.common.MenuLauncher;
import co.programmers.voucher_management.common.MenuLauncherFactory;
import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;

@Controller
@Profile("!test")
public class VoucherCommandLineRunner implements CommandLineRunner {
	private final OutputView outputView;
	private final InputView<String> inputView;
	private final MenuLauncherFactory menuLauncherFactory;

	public VoucherCommandLineRunner(OutputView outputView, InputView<String> inputView,
			MenuLauncherFactory menuLauncherFactory) {
		this.outputView = outputView;
		this.inputView = inputView;
		this.menuLauncherFactory = menuLauncherFactory;
	}

	@Override
	public void run(String... args) {
		String menu;
		do {
			outputView.printGuideMessage();
			menu = inputView.input();
			MenuLauncher menuLauncher = menuLauncherFactory.of(menu);
			Response response = menuLauncher.run();
			outputView.print(response);
		} while (!(menu.equals("x") || menu.equals("1")));
	}
}
