package org.devcourse.springbasic.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.domain.CustomerMenuType;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.devcourse.springbasic.domain.customer.service.CustomerService;
import org.devcourse.springbasic.global.io.ErrorMsgPrinter;
import org.devcourse.springbasic.global.io.input.customer.CustomerInput;
import org.devcourse.springbasic.global.io.output.customer.CustomerOutput;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public void run(CustomerInput customerInput, CustomerOutput customerOutput) {
        CustomerMenuType menu = CustomerMenuType.EXIT;
        do {
            try {
                customerOutput.menus();
                menu = customerInput.menu();
                if (menu == CustomerMenuType.SAVE) {
                    signUp(customerInput);
                }

            } catch (IllegalArgumentException e) {
                ErrorMsgPrinter.print(e.getMessage());
            }
        } while (!menu.isExit());
    }

    private void signUp(CustomerInput customerInput) {
        CustomerDto.SaveRequestDto saveRequestDto = customerInput.signUp();
        customerService.save(saveRequestDto);
    }
}
