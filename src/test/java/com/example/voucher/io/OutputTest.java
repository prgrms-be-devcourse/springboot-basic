package com.example.voucher.io;

import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Output 클래스의")
public class OutputTest {

	private final Output cw = new ConsoleWriter();
	private final OutputStream out = setMockOutputStream();

	private OutputStream setMockOutputStream() {
		OutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		return out;
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class printCommandPrompt_메서드는 {

		@Test
		@DisplayName("사용 가능한 명령어를 출력한다")
		void 사용_가능한_명령어를_출력한다 () throws Exception {
			cw.printCommandPrompt();
			String result = out.toString();
			assertThat(result).isEqualTo("=== Voucher Program === \n" +
					                     "Type exit to exit the program.\n" +
					                     "Type create to create a new voucher.\n" +
					                     "Type list to list all vouchers.\n");
		}
	}

}
