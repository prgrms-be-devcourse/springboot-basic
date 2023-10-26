package study.dev.spring.customer.presentation;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.customer.application.BlackListRepository;
import study.dev.spring.customer.application.CustomerService;
import study.dev.spring.customer.application.dto.CustomerData;
import study.dev.spring.customer.application.dto.CustomerInfo;

@Component
@RequiredArgsConstructor
public class ConsoleCustomerController {

	private static final String NEW_LINE = System.lineSeparator();

	private final BlackListRepository blackListRepository;
	private final CustomerService customerService;
	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;

	public void getAllBlackListCustomers() {
		List<CustomerData> result = blackListRepository.findAll();

		StringBuilder sb = new StringBuilder();
		result.forEach(customer -> sb.append("이름 : ").append(customer.getName()).append(NEW_LINE));

		outputHandler.showSystemMessage(sb.toString());
	}

	public void getAllCustomers() {
		List<CustomerInfo> result = customerService.getAllCustomers();

		outputHandler.showSystemMessage(generateCustomerInfoOutput(result));
	}

	public void getCustomersByVoucher() {
		outputHandler.showSystemMessage("바우처 아이디를 입력해주세요 : ");
		String voucherId = inputHandler.inputString();

		List<CustomerInfo> result = customerService.getCustomersByVoucher(voucherId);

		outputHandler.showSystemMessage(generateCustomerInfoOutput(result));
	}

	private String generateCustomerInfoOutput(List<CustomerInfo> result) {
		StringBuilder sb = new StringBuilder();
		result.forEach(customer -> {
			sb.append("아이디 : ").append(customer.id()).append(", ");
			sb.append("이름 : ").append(customer.name()).append(NEW_LINE);
		});
		return sb.toString();
	}
}
