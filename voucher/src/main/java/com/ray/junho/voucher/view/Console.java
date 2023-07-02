package com.ray.junho.voucher.view;

import com.ray.junho.voucher.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Console {

    private final InputView inputView;
    private final OutputView outputView;

    public Console(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }


    private final String START_MESSAGE = """
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.""";
    private final String EXIT_MESSAGE = "프로그램을 종료합니다.";
    private final String VOUCHER_CREATE_MESSAGE = "생성할 바우처를 입력하세요. [1. 고정 금액 할인 / 2. 퍼센트 할인]";


    public void printStartMessage() {
        outputView.print(START_MESSAGE);
    }

    public void printExitMessage() {
        outputView.print(EXIT_MESSAGE);
    }

    public void print(List<String> list) {
        list.forEach(outputView::print);
    }

    public String read() {
        return inputView.read();
    }

    public int readVoucherTypeToCreate() {
        outputView.print(VOUCHER_CREATE_MESSAGE);
        return StringUtil.convertStringToInt(inputView.read());
    }

    public int readVoucherDiscountAmount(int voucherType) {
        if (voucherType == 1) {
            outputView.print("할인할 금액을 입력하세요.");
        }
        if (voucherType == 2) {
            outputView.print("할인율을 입력하세요 (1% ~ 100%)");
        }
        return StringUtil.convertStringToInt(inputView.read());
    }

    public int readVoucherAmountToCreate() {
        outputView.print("생성할 바우처 수량을 입력해 주세요. (1개 ~ x개)");
        return StringUtil.convertStringToInt(inputView.read());
    }

    public void printCSuccessfullyCreatedMessage() {
        outputView.print("성공적으로 바우처가 생성되었습니다.");
    }
}
