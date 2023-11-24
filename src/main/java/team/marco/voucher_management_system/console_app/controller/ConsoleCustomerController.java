package team.marco.voucher_management_system.console_app.controller;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.service.CustomerService;
import team.marco.voucher_management_system.util.Console;
import team.marco.voucher_management_system.util.UUIDConverter;

@Component
public class ConsoleCustomerController {
    private static final String INFO_DELIMINATOR = MessageFormat.format("\n{0}\n", "-".repeat(42));

    private final CustomerService customerService;

    public ConsoleCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void createCustomer() {
        Console.print("이름을 입력해 주세요.");
        String name = Console.readString();

        Console.print("E-mail 주소를 입력해 주세요.");
        String email = Console.readString();

        customerService.create(name, email);
    }

    public void customerList() {
        List<Customer> customers = customerService.getCustomers();
        String infos = joinCustomersInfo(customers);

        Console.print(infos);
        Console.print("조회가 완료되었습니다.");
    }

    public void updateCustomer() {
        Console.print("수정할 고객의 ID를 입력해 주세요.");
        String input = Console.readString();
        UUID id = UUIDConverter.convert(input);

        customerService.findById(id);

        Console.print("새로운 이름을 입력해 주세요.");
        String name = Console.readString();

        customerService.update(id, name);

        Console.print("수정이 완료되었습니다.");
    }

    public void findCustomerById() {
        Console.print("조회할 고객의 ID를 입력해 주세요.");
        String input = Console.readString();
        UUID id = UUIDConverter.convert(input);

        Customer customer = customerService.findById(id);

        Console.print(customer.getInfo());
    }

    public void findCustomersByName() {
        Console.print("검색할 이름을 입력해 주세요.");
        String name = Console.readString();

        List<Customer> customers = customerService.findByName(name);
        String infos = joinCustomersInfo(customers);

        Console.print(infos);
        Console.print("검색이 완료되었습니다.");
    }

    public void findCustomersByEmail() {
        Console.print("검색할 E-mail 주소를 입력해 주세요.");
        String email = Console.readString();

        List<Customer> customers = customerService.findByEmail(email);
        String infos = joinCustomersInfo(customers);

        Console.print(infos);
        Console.print("검색이 완료되었습니다.");
    }

    private String joinCustomersInfo(List<Customer> customers) {
        if (customers.isEmpty()) {
            return "";
        }

        List<String> customerInfos = customers.stream()
                .map(Customer::getInfo)
                .toList();

        return String.join(INFO_DELIMINATOR, customerInfos);
    }
}
