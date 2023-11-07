package org.prgms.kdtspringweek1.controller.consoleController;

import org.prgms.kdtspringweek1.console.ConsoleOutput;
import org.prgms.kdtspringweek1.controller.consoleController.dto.SelectFunctionTypeDto;
import org.springframework.stereotype.Component;

// 뷰 영역(console 패키지)에 컨트롤러가 포함되어 있어, 따로 빼주었습니다.
// 기존에, 입출력 관련 기능들이 컨트롤러에 명시적으로 드러나 있어, 콘솔 입출력의 경우 따로 클래스를 두어 구현
// 프론트 컨트롤러 패턴 적용
// 1. AppController에서 각 도메인 별 컨트롤러로 분기하도록 하였습니다. -> 각 요청을 적절한 컨트롤러로 위임하는 요청 분배 역할을 가집니다.
// 2. try catch와 같은 중복 사항을 AppController에서 처리하도록 하였습니다.
@Component
public class AppController {
    private final ConsoleInputConverter consoleInputConverter;
    private final ConsoleOutput consoleOutput;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    public AppController(ConsoleInputConverter consoleInputConverter, ConsoleOutput consoleOutput, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.consoleInputConverter = consoleInputConverter;
        this.consoleOutput = consoleOutput;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    public void startVoucherProgram() {
        selectFunction();
    }

    private void selectFunction() {
        consoleOutput.printFunctionsToSelect();
        SelectFunctionTypeDto selectFunctionTypeDto = consoleInputConverter.getFunctionType();
        switch (selectFunctionTypeDto.getType()) {
            case "exit" -> exitVoucherProgram();
            case "voucher" -> voucherController.selectVoucherFunction(selectFunctionTypeDto);
            case "customer" -> customerController.selectCustomerFunction(selectFunctionTypeDto);
            case "wallet" -> walletController.selectVoucherFunction(selectFunctionTypeDto);
        }
    }

    private void exitVoucherProgram() {
        consoleOutput.printExitMessage();
        System.exit(0);
    }
}
