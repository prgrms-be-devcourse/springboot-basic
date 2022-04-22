package com.example.voucher;

import com.example.voucher.controller.VoucherController;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.io.Input;
import com.example.voucher.io.Output;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
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

	@Mock
	VoucherController voucherController;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class 애플리케이션이_실행되면 {

		private Method printCommandPrompt;
		private Method getCommand;

		@BeforeEach
		void private_메서드_테스트를_위한_설정() throws Exception {
			printCommandPrompt = voucherApplication.getClass().getDeclaredMethod("printCommandPrompt");
			printCommandPrompt.setAccessible(true);

			getCommand = voucherApplication.getClass().getDeclaredMethod("getCommand");
			getCommand.setAccessible(true);
		}

		@Test
		@DisplayName("사용 가능한 명령어를 출력한다.")
		void 사용_가능한_명령어를_출력한다 () throws Exception {
			printCommandPrompt.invoke(voucherApplication);
			verify(output).printCommandPrompt();
		}

		@Test
		@DisplayName("명령어를 입력 받는다.")
		void 명령어를_입력_받는다 () throws Exception {
			getCommand.invoke(voucherApplication);
			verify(input).getString();
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class CREATE_입력이_주어질때 {

		private Method processCreateCommand;
		private Method getVoucherType;
		private Method getDiscountAmount;

		@BeforeEach
		void private_메서드_테스트를_위한_설정() throws Exception {
			processCreateCommand = voucherApplication.getClass().getDeclaredMethod("processCreateCommand", VoucherType.class, int.class);
			processCreateCommand.setAccessible(true);

			getDiscountAmount = voucherApplication.getClass().getDeclaredMethod("getDiscountAmount");
			getDiscountAmount.setAccessible(true);

			getVoucherType = voucherApplication.getClass().getDeclaredMethod("getVoucherType");
			getVoucherType.setAccessible(true);
		}

		@Test
		@DisplayName("바우처 타입을 입력받는다")
		void 바우처_타입을_입력받는다() throws Exception {
			getVoucherType.invoke(voucherApplication);
			verify(input).getString();
		}

		@Test
		@DisplayName("할인 값을 입력받는다")
		void 할인_값을_입력받는다() throws Exception {
			getDiscountAmount.invoke(voucherApplication);
			verify(input).getInt();
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 정수가_아닌_할인_값이_입력_된다면 {

			@BeforeEach
			void 정수가_아닌_할인_값_설정() {
				given(input.getInt())
						.willThrow(new IllegalArgumentException(INVALID_INPUT.name()));
			}

			@Test
			@DisplayName("예외를 던진다")
			void 예외를_던진다() {
				assertThatThrownBy(() -> getDiscountAmount.invoke(voucherApplication))
						.isInstanceOf(InvocationTargetException.class)
						.getCause().hasMessage(INVALID_INPUT.name());
			}
		}
		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 지원하는_바우처_타입과_정수_타입의_할인_값이_입력_된다면 {

			@Test
			@DisplayName("바우처를 생성한다")
			void 바우처를_생성한다() throws Exception {
				processCreateCommand.invoke(voucherApplication, any(), anyInt());
				verify(voucherController).save(any(), anyInt());
			}
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
