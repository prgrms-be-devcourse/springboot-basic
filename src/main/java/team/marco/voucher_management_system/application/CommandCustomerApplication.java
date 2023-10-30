package team.marco.voucher_management_system.application;

import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.ConsoleCustomerController;
import team.marco.voucher_management_system.type_enum.CustomerCommandType;
import team.marco.voucher_management_system.util.Console;

@Component
public class CommandCustomerApplication extends RunnableCommandApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandCustomerApplication.class);

    private final ConsoleCustomerController customerController;

    public CommandCustomerApplication(ConsoleCustomerController customerController) {
        this.customerController = customerController;
    }

    @Override
    protected void start() {
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
            CustomerCommandType userCommand = CustomerCommandType.selectCommand(userInput);

            switchCommand(userCommand);
        } catch (NumberFormatException e) {
            logger.warn(e.toString());
            Console.print("숫자를 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            logger.warn(e.toString());
            Console.print(e.getMessage());
        } catch (DataSourceLookupFailureException e) {
            logger.error(e.toString());
            Console.print(e.getMessage());
        } catch (DuplicateKeyException e) {
            logger.error(e.toString());
            Console.print("이미 존재하는 이메일 입니다.");
        } catch (NoSuchElementException e) {
            logger.error(e.toString());
            Console.print("존재하지 않는 ID 입니다.");
        }
    }

    private void switchCommand(CustomerCommandType userCommand) {
        switch (userCommand) {
            case EXIT -> runningFlag = false;
            case CREATE -> customerController.createCustomer();
            case LIST -> customerController.customerList();
            case UPDATE -> customerController.updateCustomer();
            case FIND_BY_ID -> customerController.findCustomerById();
            case FIND_BY_NAME -> customerController.findCustomersByName();
            case FIND_BY_EMAIL -> customerController.findCustomersByEmail();
        }
    }

    @Override
    protected void close() {
        Console.print("초기 화면으로 돌아갑니다.");
    }
}
