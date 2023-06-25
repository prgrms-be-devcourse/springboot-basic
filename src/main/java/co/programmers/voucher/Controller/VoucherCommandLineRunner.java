package co.programmers.voucher.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import co.programmers.voucher.Launcher;
import co.programmers.voucher.MenuLauncher;
import co.programmers.voucher.View.InputView;
import co.programmers.voucher.View.OutputView;

@Controller
public class VoucherCommandLineRunner implements VoucherApplicationRunner {
	private final OutputView outputView;
	private final InputView inputView;

	@Autowired
	public VoucherCommandLineRunner(OutputView outputView, InputView inputView) {
		this.outputView = outputView;
		this.inputView = inputView;
	}

	public void run() throws IOException {
		String menu;
		do {
			outputView.printGuideMessage();
			menu = (String)inputView.input();
			Launcher launcher = MenuLauncher.from(menu);
			Map<String, Object> demandedData = collectDemandedData(launcher.getRequestBody());
			launcher.run(demandedData);
		} while (!"EXIT".equalsIgnoreCase(menu));
	}

	Map<String, Object> collectDemandedData(Map<String, Object> requestBody) throws IOException {
		Map<String, Object> responseBody = new HashMap<>();
		for (var value : requestBody.entrySet()) {
			String instructionMessage = makeMessage(value);
			outputView.print(instructionMessage);
			Object userData = inputView.input();
			responseBody.put(value.getKey(), userData);
		}
		return responseBody;
	}

	private String makeMessage(Map.Entry<String, Object> variableFactor) {
		StringBuffer message = new StringBuffer();
		String factorName = variableFactor.getKey();
		String explanation = (String)variableFactor.getValue();
		message.append("Input ");
		message.append(factorName);
		message.append("\n" + explanation);
		message.append(">> ");
		return message.toString();
	}
}
