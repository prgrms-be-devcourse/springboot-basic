package com.example.voucher.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static com.example.voucher.exception.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@Rollback
@DisplayName("FileUtils 클래스의")
public class FileUtilsTest {

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class readFile메서드는 {

		private final String path = "읽어올 파일 경로";
		private final String content = "테스트 문자열";

		@BeforeEach
		void 파일_저장_설정() {
			try {
				FileWriter fw = new FileWriter(path, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		@AfterEach
		void 생성된_파일_삭제() {
			new File(path).delete();
		}

		@Test
		@DisplayName("파일에 저장된 내용을 읽고 반환한다")
		void 파일에_저장된_내용을_읽고_반환한다() {
			List<String> fileStringList = FileUtils.readFile(path);
			assertThat(fileStringList.size()).isEqualTo(1);
			assertThat(fileStringList.get(0)).isEqualTo(content);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 존재하지_않는_파일_경로가_주어진다면 {

			private final String path = "존재하지 않는 파일 경로";

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> FileUtils.readFile(path))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(FILE_READ_ERROR.getMessage());
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 저장된_내용이_없다면 {

			private final String path = "내용이 없는 파일 경로";

			@BeforeEach
			void 파일_저장_설정() {
				try {
					new File(path).createNewFile();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}

			@AfterEach
			void 생성된_파일_삭제() {
				new File(path).delete();
			}

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() {
				List<String> fileStringList = FileUtils.readFile(path);
				assertThat(fileStringList.size()).isEqualTo(0);
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class writeFile메서드는 {
		private final String path = "저장할 파일 경로";
		private final String content = "테스트 문자열";

		@AfterEach
		void 생성된_파일_삭제() {
			new File(path).delete();
		}

		@Test
		@DisplayName("파일에 내용을 저장한다")
		void 파일에_내용을_저장한다() {
			FileUtils.writeFile(path, content);

			List<String> fileStringList = FileUtils.readFile(path);
			assertThat(fileStringList.size()).isEqualTo(1);
			assertThat(fileStringList.get(0)).isEqualTo(content);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 존재하지_않는_파일_경로가_주어진다면 {

			private final String path = "존재하지 않는 파일 경로";

			@AfterEach
			void 생성된_파일_삭제() {
				new File(path).delete();
			}

			@Test
			@DisplayName("파일을 생성한다")
			void 파일을_생성한다() {
				FileUtils.writeFile(path, content);
				assertThat(new File(path).exists());
			}
		}
	}
}