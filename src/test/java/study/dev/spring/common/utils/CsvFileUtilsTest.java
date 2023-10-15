package study.dev.spring.common.utils;

import static org.assertj.core.api.Assertions.*;
import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import study.dev.spring.common.exception.GlobalException;

@DisplayName("[CsvFileUtils Test] - Infrastructure Layer")
class CsvFileUtilsTest {

	private static final String FILE_PATH = "test.csv";

	private final CsvFileUtils fileUtils;

	public CsvFileUtilsTest() {
		fileUtils = new CsvFileUtils();
	}

	@AfterEach
	void resetData() {
		List<Object> data = List.of(new TestUser("hello", 10), new TestUser("good", 20));
		fileUtils.writeFile(FILE_PATH, data);
	}

	@Test
	@DisplayName("[파일이 해당 클래스에서 지원하는 파일인지 확인한다]")
	void isSupportedTest() {
		//given
		final String csvFilePath = "hello.csv";
		final String jsonFilePath = "hello.json";

		//when
		boolean csvActual = fileUtils.isSupported(csvFilePath);
		boolean jsonActual = fileUtils.isSupported(jsonFilePath);

		//then
		assertThat(csvActual).isTrue();
		assertThat(jsonActual).isFalse();
	}

	@Nested
	@DisplayName("[CSV 파일을 읽는다]")
	class readFile {

		@Test
		@DisplayName("[성공적으로 CSV 파일을 읽는다]")
		void success() {
			//when
			List<Object> objects = fileUtils.readFile(FILE_PATH, TestUser.class);

			//then
			assertThat(objects).contains(new TestUser("hello", 10), new TestUser("good", 20));
		}

		@Test
		@DisplayName("[파일 경로에 파일이 존재하지 않아 실패한다]")
		void failByInvalidFilePath() {
			//given
			final String invalidFilePath = "invalid";

			//when
			ThrowingCallable when = () -> fileUtils.readFile(invalidFilePath, TestUser.class);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(GlobalException.class)
				.hasMessageContaining(FILE_READ_EX.getMessage());
		}
	}

	@Nested
	@DisplayName("[CSV 파일을 저장한다]")
	class writeFile {

		@Test
		@DisplayName("[성공적으로 CSV 파일을 저장한다]")
		void success() {
			//given
			TestUser steve = new TestUser("steve", 20);
			List<Object> data = List.of(steve);

			//when
			fileUtils.writeFile(FILE_PATH, data);

			//then
			List<Object> actual = fileUtils.readFile(FILE_PATH, TestUser.class);
			assertThat(actual).contains(steve);
		}

		@Test
		@DisplayName("[파일 경로에 파일이 존재하지 않아 실패한다]")
		void failByInvalidFilePath() {
			//given
			final String invalidFilePath = "invalid";

			//when
			ThrowingCallable when = () -> fileUtils.writeFile(invalidFilePath, new ArrayList<>());

			//then
			assertThatThrownBy(when)
				.isInstanceOf(GlobalException.class)
				.hasMessageContaining(FILE_WRITE_EX.getMessage());
		}
	}
}
