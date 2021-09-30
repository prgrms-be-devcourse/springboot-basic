package org.prgrms.kdt.application.cmd.view;

import org.prgrms.kdt.common.view.console.io.Console;
import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.util.List;

public class CommandLineView {

    private final static String CMD_REQUEST_MSG = "커맨드를 입력하세요 (exit, create_voucher, list_voucher, list_customer) : ";
    private final static String VOUCHER_TYPE_REQUEST_MSG = "바우처 타입 번호를 입력하세요 (1-FixedAmount, 2-PrecentDiscount) : ";
    private final static String DISCOUNT_VALUE_REUQEST_MSG = "할인 값을 입력하세요 :  ";
    private final static String VOUCHER_COUNTER_OUPUT_MSG = "[총 {0}개의 바우처가 있습니다.]";
    private final static String CUSTOMER_COUNTER_OUPUT_MSG = "[총 {0}개의 고객 명단이 있습니다.]";
    private final static String EXIT_MSG = "프로그램을 종료합니다.";
    private final static String START_MSG = "============== 바우처 프로그램 ==============\n"
                                            + "          exit 커맨드 : 프로그램 종료\n"
                                            + "create_voucher 커맨드 : 바우처 생성\n"
                                            + "  list_voucher 커맨드 : 모든 바우처 조회\n"
                                            + " list_customer 커맨드 : 모든 고객 조회\n"
                                            + "=========================================";

    private Console console;

    public CommandLineView() {
        console = new Console();
    }

    public void showStartMessage(){
        console.showMessage(START_MSG);
    }

    public String requestCommand() {
        return console.input(CMD_REQUEST_MSG);
    }

    public String requestVoucherType(){
        return console.input(VOUCHER_TYPE_REQUEST_MSG);
    }

    public String requestDiscountValue(){
        return console.input(DISCOUNT_VALUE_REUQEST_MSG);
    }

    public void showVoucherList(List<Voucher> vouchers){
        console.showMessage(MessageFormat.format(VOUCHER_COUNTER_OUPUT_MSG, vouchers.size()));

        for(Voucher voucher : vouchers){
            console.showMessage(voucher.toString());
        }
    }

    public void showCustomerList(List<Customer> customers){
        console.showMessage(MessageFormat.format(CUSTOMER_COUNTER_OUPUT_MSG, customers.size()));

        for(Customer customer : customers){
            console.showMessage(customer.toString());
        }
    }

    public void showErrorMessage(String errorMessage){
        console.showMessage("[Error]" + errorMessage);
    }

    public void close(){
        console.showMessage(EXIT_MSG);
        console.close();
    }
}
