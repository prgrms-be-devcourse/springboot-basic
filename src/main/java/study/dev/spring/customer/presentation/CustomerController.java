package study.dev.spring.customer.presentation;

import java.util.List;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.customer.domain.Customer;
import study.dev.spring.customer.domain.CustomerRepository;

@Controller
@RequiredArgsConstructor
public class CustomerController {

	private static final String NEW_LINE = System.lineSeparator();

	private final CustomerRepository customerRepository;
	private final OutputHandler outputHandler;

	public void findAllBlackListCustomers() {
		List<Customer> customers = customerRepository.findAll();

		StringBuilder sb = new StringBuilder();
		customers.forEach(customer -> sb.append("이름 : ").append(customer.getName()).append(NEW_LINE));

		outputHandler.showSystemMessage(sb.toString());
	}
}
