package study.dev.spring.customer.presentation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import study.dev.spring.common.io.ConsoleOutputHandler;
import study.dev.spring.customer.stub.BlackListRepositoryStub;

@DisplayName("[ConsoleCustomerController Test] - Presentation Layer")
class ConsoleCustomerControllerTest {

	private final ConsoleCustomerController consoleCustomerController = new ConsoleCustomerController(
		new BlackListRepositoryStub(), new ConsoleOutputHandler()
	);

	@Test
	@DisplayName("[모든 블랙리스트 회원을 조회하는 프로세스를 실행한다]")
	void findAllBlackListCustomersTest() {
		//when
		Executable when = consoleCustomerController::findAllBlackListCustomers;

		//then
		assertDoesNotThrow(when);
	}
}
