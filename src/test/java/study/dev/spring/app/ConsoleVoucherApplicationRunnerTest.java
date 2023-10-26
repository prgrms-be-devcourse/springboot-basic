package study.dev.spring.app;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;

@DisplayName("[ConsoleVoucherApplicationRunner Test] - App")
class ConsoleVoucherApplicationRunnerTest {

	private final ConsoleVoucherApplicationRunner runner;
	private final CommandExecutor commandExecutor;
	private final InputHandler mockInputHandler;

	public ConsoleVoucherApplicationRunnerTest() {
		commandExecutor = Mockito.mock(CommandExecutor.class);
		mockInputHandler = Mockito.mock(InputHandler.class);
		OutputHandler mockOutputHandler = Mockito.mock(OutputHandler.class);

		this.runner = new ConsoleVoucherApplicationRunner(
			commandExecutor, mockInputHandler, mockOutputHandler
		);
	}

	@Nested
	@DisplayName("[바우처 애플리케이션을 실행한다]")
	class runTest {

		@Test
		@DisplayName("[종료 기능을 실행시킨다]")
		void exitTest() {
			//given
			given(mockInputHandler.inputString())
				.willReturn("exit");

			//when
			runner.run();

			//then
			verify(commandExecutor, times(1)).exit();
		}

		@Test
		@DisplayName("[바우처 생성 기능을 실행시킨다]")
		void createTest() {
			//given
			given(mockInputHandler.inputString())
				.willReturn("create_voucher");

			//when
			runner.run();

			//then
			verify(commandExecutor, times(1)).createVoucher();
		}

		@Test
		@DisplayName("[바우처 전체조회 기능을 실행시킨다]")
		void listTest() {
			//given
			given(mockInputHandler.inputString())
				.willReturn("all_vouchers");

			//when
			runner.run();

			//then
			verify(commandExecutor, times(1)).allVouchers();
		}

		@Test
		@DisplayName("[블랙리스트 조회 기능을 실행시킨다]")
		void blackListTest() {
			//given
			given(mockInputHandler.inputString())
				.willReturn("black_list");

			//when
			runner.run();

			verify(commandExecutor, times(1)).blackList();
		}
	}
}