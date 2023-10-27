package team.marco.voucher_management_system.application;

import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.ConsoleWalletController;
import team.marco.voucher_management_system.type_enum.WalletCommandType;
import team.marco.voucher_management_system.util.Console;

@Component
public class CommandWalletApplication extends RunnableCommandApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandWalletApplication.class);

    private final ConsoleWalletController walletController;

    public CommandWalletApplication(ConsoleWalletController walletController) {
        this.walletController = walletController;
    }

    @Override
    protected void start() {
        Console.print("""
                === 쿠폰 관리 매뉴 ===
                0. 쿠폰 관리 종료
                1. 쿠폰 지급
                2. 보유 중인 쿠폰 목록
                3. 쿠폰 반납
                4. 쿠폰을 보유 중인 고객 목록""");

        int userInput = Console.readInt();

        executeCommand(userInput);
    }

    private void executeCommand(int userInput) {
        try {
            WalletCommandType userCommand = WalletCommandType.selectCommand(userInput);

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
        } catch (DuplicateKeyException e) {
            logger.error(e.toString());
            Console.print("이미 존재하는 쿠폰입니다.");
        }
    }

    private void switchCommand(WalletCommandType userCommand) {
        switch (userCommand) {
            case EXIT -> runningFlag = false;
            case SUPPLY -> walletController.supplyVoucher();
            case VOUCHER_LIST -> walletController.voucherList();
            case RETURN -> walletController.returnVoucher();
            case CUSTOMER_LIST -> walletController.customerList();
        }
    }

    @Override
    protected void close() {
        Console.print("초기 화면으로 돌아갑니다.");
    }
}
