package team.marco.vouchermanagementsystem.controller;

import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import team.marco.vouchermanagementsystem.application.ConsoleApplication;
import team.marco.vouchermanagementsystem.model.Customer;
import team.marco.vouchermanagementsystem.service.CustomerService;
import team.marco.vouchermanagementsystem.util.Console;

@Controller
public class ConsoleCustomerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);
    private static final String INFO_DELIMINATOR = "\n----------------------\n";

    private final CustomerService customerService;

    private boolean runningFlag;

    public ConsoleCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void run() {
        runningFlag = true;

        while (runningFlag) {
            selectCommand();
        }
    }

    public void selectCommand() {
        Console.print("""
                === 사용자 관리 매뉴 ===
                0. 사용자 관리 종료
                1. 사용자 추가
                2. 사용자 목록 조회
                3. 사용자 정보 수정
                4. ID로 조회
                5. 이름으로 검색
                6. E-mail로 검색""");

        int userInput = Console.readInt();

        executeCommand(userInput);
    }

    private void executeCommand(int userInput) {
        try {
            CustomerCommand userCommand = CustomerCommand.selectCommand(userInput);

            switchCommand(userCommand);
        } catch (NumberFormatException e) {
            logger.warn(e.toString());
            Console.print("숫자를 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            logger.warn(e.toString());
            Console.print(e.getMessage());
        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            logger.error(e.toString());
            Console.print("존재하지 않는 ID 입니다.");
        } catch (DataAccessResourceFailureException e) {
            logger.error(e.toString());
            Console.print(e.getMessage());
        }
    }

    private void switchCommand(CustomerCommand userCommand) {
        switch (userCommand) {
            case EXIT -> runningFlag = false;
            case CREATE -> createCustomer();
            case LIST -> customerList();
            case UPDATE -> updateCustomer();
            case FIND_BY_ID -> findCustomerById();
            case FIND_BY_NAME -> findCustomersByName();
            case FIND_BY_EMAIL -> findCustomersByEmail();
        }
    }

    private void createCustomer() {
        Console.print("이름을 입력해 주세요.");
        String name = Console.readString();

        Console.print("E-mail 주소를 입력해 주세요.");
        String email = Console.readString();

        customerService.create(name, email);
    }

    private void customerList() {
        List<Customer> customers = customerService.getCustomers();
        String infos = joinCustomersInfo(customers);

        Console.print(infos);
        Console.print("조회가 완료되었습니다.");
    }

    private void updateCustomer() {
        Console.print("수정할 고객의 ID를 입력해 주세요.");
        String id = Console.readString();

        customerService.findById(id);

        Console.print("새로운 이름을 입력해 주세요.");
        String name = Console.readString();

        customerService.update(id, name);

        Console.print("수정이 완료되었습니다.");
    }

    private void findCustomerById() {
        Console.print("조회할 고객의 ID를 입력해 주세요.");
        String id = Console.readString();

        Customer customer = customerService.findById(id);

        Console.print(customer.getInfo());
    }

    private void findCustomersByName() {
        Console.print("검색할 이름을 입력해 주세요.");
        String name = Console.readString();

        List<Customer> customers = customerService.findByName(name);
        String infos = joinCustomersInfo(customers);

        Console.print(infos);
        Console.print("조회가 완료되었습니다.");
    }

    private void findCustomersByEmail() {
        Console.print("검색할 E-mail 주소를 입력해 주세요.");
        String email = Console.readString();

        List<Customer> customers = customerService.findByEmail(email);
        String infos = joinCustomersInfo(customers);

        Console.print(infos);
        Console.print("조회가 완료되었습니다.");
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
