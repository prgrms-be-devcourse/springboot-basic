package org.programmers.springbootbasic;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.io.Input;
import org.programmers.springbootbasic.io.Output;
import org.programmers.springbootbasic.util.ConstantMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class VoucherExecutor {

    private final Input input;
    private final Output output;


    public void run() throws IOException {

        while(true) {
            output.printMenu(ConstantMessageUtil.VOUCHER_MAIN_MENU);
            VoucherMainMenuCommand menuInput = input.getVoucherMainMenuInput(ConstantMessageUtil.TYPE_USER_COMMAND);
            switch (menuInput) {
                case CREATE -> {
                    /*
                    voucher service 의 createVoucher 실행
                     */
                }
                case LIST -> {
                    /*
                    voucher service의 findAll
                     */
                }
                case EXIT -> {
                    /*
                    return(?)
                     */
                }
                default -> {
                    /*
                    output의 error 출력
                     */
                }
            }
        }
    }
}
