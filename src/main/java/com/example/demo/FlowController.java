package com.example.demo;


import com.example.demo.controller.CustomerController;
import com.example.demo.controller.VoucherController;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.VoucherDto;
import com.example.demo.util.CommandType;
import com.example.demo.util.VoucherType;
import com.example.demo.view.customer.CustomerView;
import com.example.demo.view.voucher.VoucherView;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlowController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(FlowController.class);

    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final VoucherView voucherView;
    private final CustomerView customerView;

    @Override
    public void run(String... args) throws Exception {
        boolean shouldContinue = true;
        while (shouldContinue) {
            try {
                CommandType commandType = voucherView.readCommandOption();

                switch (commandType) {
                    case CREATE_VOUCHER -> {
                        VoucherType voucherType = voucherView.readVoucherOption();
                        int amount = voucherView.readVoucherAmount(voucherType);
                        VoucherDto voucherDto = voucherController.create(voucherType, amount);
                        voucherView.printCreateMessage(voucherDto);
                    }
                    case PRINT_VOUCHER_LIST -> {
                        List<VoucherDto> voucherDtoList = voucherController.readList();
                        voucherView.printVoucherList(voucherDtoList);
                    }
                    case CREATE_CUSTOMER -> {
                        String name = customerView.readCustomerName();
                        int age = customerView.readCustomerAge();
                        CustomerDto customerDto = customerController.create(name, age);
                        customerView.printCreateMessage(customerDto);
                    }
                    case PRINT_CUSTOMER_LIST -> {
                        List<CustomerDto> customerDtoList = customerController.readList();
                        customerView.printVoucherList(customerDtoList);
                    }
                    case UPDATE_CUSTOMER -> {
                        UUID id = customerView.readCustomerId();
                        String name = customerView.readCustomerName();
                        customerController.updateName(id, name);
                        customerView.printUpdateMessage();
                    }
                    case DELETE_CUSTOMER -> {
                        UUID id = customerView.readCustomerId();
                        customerController.deleteById(id);
                        customerView.printDeleteMessage();
                    }
                    case EXIT -> shouldContinue = false;
                    default -> throw new IllegalArgumentException(String.format("입력하신 %s은 올바르지 않은 커맨드입니다.", commandType.name()));
                }
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
