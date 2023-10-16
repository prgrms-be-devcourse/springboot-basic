package study.dev.spring.app;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import study.dev.spring.app.exception.ExitException;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.voucher.presentation.VoucherController;

@DisplayName("[VoucherApplicationRunner Test] - App")
@ExtendWith(MockitoExtension.class)
class VoucherApplicationRunnerTest {

	private final VoucherApplicationRunner runner;
	private final InputHandler mockInputHandler;
	private final VoucherController mockController;

	public VoucherApplicationRunnerTest() {
		mockController = Mockito.mock(VoucherController.class);
		mockInputHandler = Mockito.mock(InputHandler.class);
		OutputHandler mockOutputHandler = Mockito.mock(OutputHandler.class);

		this.runner = new VoucherApplicationRunner(
			mockController, mockInputHandler, mockOutputHandler
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
			ThrowingCallable when = runner::run;

			//then
			assertThatThrownBy(when).isInstanceOf(ExitException.class);
		}

		@Test
		@DisplayName("[바우처 생성 기능을 실행시킨다]")
		void createTest() {
			//given
			given(mockInputHandler.inputString())
				.willReturn("create");

			//when
			runner.run();

			//then
			verify(mockController, times(1)).createVoucher();
		}

		@Test
		@DisplayName("[바우처 전체조회 기능을 실행시킨다]")
		void listTest() {
			//given
			given(mockInputHandler.inputString())
				.willReturn("list");

			//when
			runner.run();

			//then
			verify(mockController, times(1)).findAllVouchers();
		}
	}
}