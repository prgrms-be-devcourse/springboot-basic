package study.dev.spring.customer.presentation;

import java.util.List;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.customer.application.BlackListRepository;
import study.dev.spring.customer.application.dto.CustomerData;

@Controller
@RequiredArgsConstructor
public class CustomerController {

	private static final String NEW_LINE = System.lineSeparator();

	private final BlackListRepository blackListRepository;
	private final OutputHandler outputHandler;

	public void findAllBlackListCustomers() {
		List<CustomerData> customers = blackListRepository.findAll();

		StringBuilder sb = new StringBuilder();
		customers.forEach(customer -> sb.append("이름 : ").append(customer.getName()).append(NEW_LINE));

		outputHandler.showSystemMessage(sb.toString());
	}
}
