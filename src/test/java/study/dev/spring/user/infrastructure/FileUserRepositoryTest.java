package study.dev.spring.user.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.dev.spring.common.stub.FileUtilsStub;
import study.dev.spring.user.domain.User;

@DisplayName("[FileUserRepository Test] - Infrastructure Layer")
class FileUserRepositoryTest {

	private final FileUserRepository fileUserRepository = new FileUserRepository(
		List.of(new FileUtilsStub()), "path"
	);

	@Test
	@DisplayName("[모든 유저를 조회한다]")
	void findAllTest() {
		//when
		List<User> actual = fileUserRepository.findAll();

		//then -> save 로직이 없기 때문에 비어있는지만 검사
		assertThat(actual).isEmpty();
	}
}