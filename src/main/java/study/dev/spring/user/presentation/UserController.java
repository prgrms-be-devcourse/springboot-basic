package study.dev.spring.user.presentation;

import java.util.List;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.user.domain.User;
import study.dev.spring.user.domain.UserRepository;

@Controller
@RequiredArgsConstructor
public class UserController {

	private static final String NEW_LINE = System.lineSeparator();

	private final UserRepository userRepository;
	private final OutputHandler outputHandler;

	public void findAllBlackListUsers() {
		List<User> users = userRepository.findAll();

		StringBuilder sb = new StringBuilder();
		users.forEach(user -> sb.append("이름 : ").append(user.getName()).append(NEW_LINE));

		outputHandler.showSystemMessage(sb.toString());
	}
}
