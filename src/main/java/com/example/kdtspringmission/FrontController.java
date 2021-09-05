package com.example.kdtspringmission;

import static com.example.kdtspringmission.Command.*;

import com.example.kdtspringmission.customer.ui.DeleteVoucherController;
import com.example.kdtspringmission.customer.ui.FindOwnerController;
import com.example.kdtspringmission.customer.ui.ListCustomerController;
import com.example.kdtspringmission.customer.ui.AssignVoucherController;
import com.example.kdtspringmission.customer.ui.ListOwnerController;
import com.example.kdtspringmission.voucher.ui.CreateVoucherController;
import com.example.kdtspringmission.voucher.ui.ListVoucherController;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class FrontController {

    private final ApplicationContext ac;
    private Map<Command, Handler> handlerMappingMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public FrontController(ApplicationContext ac) {
        this.ac = ac;
        initHandlerMappingMap();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put(CREATE, ac.getBean(CreateVoucherController.class));
        handlerMappingMap.put(LIST, ac.getBean(ListVoucherController.class));
        handlerMappingMap.put(CUSTOMERS, ac.getBean(ListCustomerController.class));
        handlerMappingMap.put(ASSIGN_VOUCHER, ac.getBean(AssignVoucherController.class));
        handlerMappingMap.put(LIST_BY_OWNER, ac.getBean(ListOwnerController.class));
        handlerMappingMap.put(DELETE_VOUCHER, ac.getBean(DeleteVoucherController.class));
        handlerMappingMap.put(FIND_OWNER, ac.getBean(FindOwnerController.class));
    }

    public void run() {
        while (true) {
            commandList();
            executeCommand(getCommand());
        }
    }

    public void executeCommand(Command command) {
        if (command == EXIT) {
            System.exit(0);
            return;
        }

        Handler handler = handlerMappingMap.get(command);
        if (handler == null) {
            throw new IllegalArgumentException("No such command " + command);
        }

        ModelAndView mv = handler.process(scanner);
        printResult(mv);
    }

    private void commandList() {
        System.out.println("=== Voucher Program ===\n"
            + "Type 'exit' to exit the program\n"
            + "Type 'create' to create voucher\n"
            + "Type 'list' to list vouchers\n"
            + "Type 'blacklist' to list blacklist\n"
            + "Type 'customers' to list customers\n"
            + "Type 'assign_voucher' to assign a voucher to a customer\n"
            + "Type 'list_by_owner' to list vouchers the customer has\n"
            + "Type 'delete_voucher' to remove voucher from the customer's wallet\n"
            + "Type 'find_owner' to find a customer who own a particular voucher");
    }

    private Command getCommand() {
        String input = scanner.nextLine();
        return Command.valueOf(input.toUpperCase());
    }

    private void printResult(ModelAndView mv) {
        String viewName = mv.getViewName();
        Map<String, Object> model = mv.getModel();

        System.out.println(viewName);
        System.out.println();
        model.values().forEach(System.out::println);
    }

}
