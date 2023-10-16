package study.dev.spring.user.presentation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import study.dev.spring.common.io.ConsoleOutputHandler;
import study.dev.spring.user.stub.UserRepositoryStub;

@DisplayName("[UserController Test] - Presentation Layer")
class UserControllerTest {

	private final UserController userController = new UserController(
		new UserRepositoryStub(), new ConsoleOutputHandler()
	);

	@Test
	@DisplayName("[모든 블랙리스트 회원을 조회하는 프로세스를 실행한다]")
	void findAllBlackListUsersTest() {
		//when
		Executable when = userController::findAllBlackListUsers;

		//then
		assertDoesNotThrow(when);
	}
}
