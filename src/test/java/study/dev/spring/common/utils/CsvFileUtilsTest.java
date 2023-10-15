package study.dev.spring.common.utils;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import study.dev.spring.common.exception.GlobalException;

@DisplayName("[CsvFileUtils Test] - Infrastructure Layer")
class CsvFileUtilsTest {

	private static final String FILE_PATH = "files/test.csv";
	private final CsvFileUtils fileUtils = new CsvFileUtils();
	private final ObjectMapper objectMapper = new ObjectMapper();

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
			Object actual = fileUtils.readFile(FILE_PATH);

			//then
			TypeReference<List<TestUser>> typeReference = new TypeReference<>() {
			};
			List<TestUser> result = objectMapper.convertValue(actual, typeReference);

			assertThat(result).containsAll(List.of(new TestUser("hello", 10), new TestUser("good", 20)));
		}

		@Test
		@DisplayName("[파일 경로에 파일이 존재하지 않아 실패한다]")
		void failByInvalidFilePath() {
			//given
			final String invalidFilePath = "invalid";

			//when
			ThrowingCallable when = () -> fileUtils.readFile(invalidFilePath);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(GlobalException.class)
				.hasMessageContaining(FILE_READ_EX.getMessage());
		}
	}

	private record TestUser(
		String name,
		int age
	) {
	}
}
