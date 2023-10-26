package study.dev.spring.app;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;

@RequiredArgsConstructor
public class ConsoleVoucherApplicationRunner {

	private final CommandExecutor commandExecutor;
	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;

	public void run() {
		outputHandler.showStartMessage();
		outputHandler.showSystemMessage("사용할 기능을 입력해주세요 : ");
		String function = inputHandler.inputString();

		Command.execute(commandExecutor, function);
	}
}
