package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputView implements OutputView {

    private static final String MENU_TITLE = "[할인권 프로그램 v1.0]";
    private static final String INPUT_MESSAGE = "입력 : ";
    private static final String QUIT_MESSAGE = "프로그램을 종료합니다.";
    private static final String VOUCHER_TYPE_SELECTION_MESSAGE = "할인 방식을 선택하세요.";
    private static final String FIX_DISCOUNT_PRICE_INPUT_MESSAGE = "고정 할인 금액을 입력하세요.";
    private static final String FIX_DISCOUNT_PRICE_CONDITION_MESSAGE = "1이상의 자연수만 입력하세요. 단위는 원입니다.";
    private static final String PERCENT_DISCOUNT_RATE_INPUT_MESSAGE = "비율 할인 퍼센트를 입력하세요.";
    private static final String PERCENT_DISCOUNT_RATE_CONDITION_MESSAGE = "1이상 100이하의 자연수만 입력하세요. 단위는 %입니다.";
    private static final String VOUCHER_CREATION_SUCCESS_MESSAGE = "할인권 생성이 완료되었습니다.";
    private static final String ALL_VOUCHERS_LIST_MESSAGE = "현재까지 생성된 할인권 목록입니다.";

    @Override
    public void showMenu() {
        printWithLineBreak(MENU_TITLE);

        for (Menu menu : Menu.values()) {
            printWithLineBreak(menu.toString());
        }

        printWithLineBreak();
        printWithoutLineBreak(INPUT_MESSAGE);
    }

    @Override
    public void showQuitMessage() {
        printWithLineBreak();
        printWithLineBreak(QUIT_MESSAGE);
    }

    @Override
    public void showVoucherTypes() {
        printWithLineBreak();
        printWithLineBreak(VOUCHER_TYPE_SELECTION_MESSAGE);

        for (VoucherType type : VoucherType.values()) {
            printWithLineBreak(type.toString());
        }

        printWithoutLineBreak(INPUT_MESSAGE);
    }

    @Override
    public void showFixDiscountPriceInputMessage() {
        printWithLineBreak();
        printWithLineBreak(FIX_DISCOUNT_PRICE_INPUT_MESSAGE);
        printWithLineBreak(FIX_DISCOUNT_PRICE_CONDITION_MESSAGE);
        printWithoutLineBreak(INPUT_MESSAGE);
    }

    @Override
    public void showPercentDiscountRateInputMessage() {
        printWithLineBreak();
        printWithLineBreak(PERCENT_DISCOUNT_RATE_INPUT_MESSAGE);
        printWithLineBreak(PERCENT_DISCOUNT_RATE_CONDITION_MESSAGE);
        printWithoutLineBreak(INPUT_MESSAGE);
    }

    @Override
    public void showVoucherCreationSuccessMessage(String voucherInfo) {
        printWithLineBreak();
        printWithLineBreak(VOUCHER_CREATION_SUCCESS_MESSAGE);
        printWithLineBreak(voucherInfo);
        printWithLineBreak();
    }

    @Override
    public void showAllVouchers(Collection<Voucher> vouchers) {
        printWithLineBreak();
        printWithLineBreak(ALL_VOUCHERS_LIST_MESSAGE);

        for (Voucher voucher : vouchers) {
            printWithLineBreak(voucher.toString());
        }

        printWithLineBreak();
    }

    @Override
    public void printWithLineBreak() {
        System.out.println();
    }

    @Override
    public void printWithLineBreak(String data) {
        System.out.println(data);
    }

    @Override
    public void printWithoutLineBreak(String data) {
        System.out.print(data);
    }
}
