package com.example.voucher;

import com.example.voucher.io.Input;
import com.example.voucher.io.Output;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import java.lang.reflect.Method;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoucherApplication 클래스는")
class VoucherApplicationTests {

	@InjectMocks
	VoucherApplication voucherApplication;

	@Mock
	Input input;

	@Mock
	Output output;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class 애플리케이션이_실행되면 {
		private Method getCommand;

		@BeforeEach
		void private_메서드_테스트를_위한_설정() throws Exception {
			getCommand = voucherApplication.getClass().getDeclaredMethod("getCommand");
			getCommand.setAccessible(true);
		}

		@Test
		@DisplayName("사용 가능한 명령어를 출력하고 명령어를 입력 받는다.")
		void 사용_가능한_명령어를_출력하고_명령어를_입력_받는다 () throws Exception {
			getCommand.invoke(voucherApplication);
			verify(output).printCommandPrompt();
			verify(input).getCommand();
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class CREATE_입력이_주어지면 {

		@Test
		@DisplayName("바우처를 생성하고 생성된 바우처를 출력한다")
		void 바우처를_생성하고_생성된_바우처를_출력한다 () {

		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class LIST_입력이_주어지면 {

		@Test
		@DisplayName("바우처를 전체 조회하고 출력한다")
		void 바우처를_전체_조회하고_출력한다 () {
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class EXIT_입력이_주어지면 {

		@Test
		@DisplayName("애플리케이션을 종료한다")
		void 애플리케이션을_종료한다 () {
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class 이외의_입력이_주어지면 {

		@Test
		@DisplayName("예외를 출력한다")
		void 예외를_출력한다 () {
		}
	}
}
