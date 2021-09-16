package prgms.springbasic.BlackCustomer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.springbasic.io.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class CustomerBlacklistApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerBlacklistAppConfig.class);
        BlackCustomerService customerService = applicationContext.getBean(BlackCustomerService.class);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Printer printer = new Printer();

        while (true) {
            printer.printBlackCustomerCommandList();
            String command = bufferedReader.readLine();
            if (command.equals("save")) {
                printer.customerNameInput();
                String name = bufferedReader.readLine();
                BlackCustomer customer = customerService.saveCustomer(name);
                printer.printBlackCustomerSaveSuccess(customer);

            } else if (command.equals("list")) {
                List<BlackCustomer> customerList = customerService.getCustomerList();
                if(customerList.isEmpty()){
                    customerService.listIsEmpty();
                    continue;
                }
                printer.printBlackCustomerList(customerList);

            } else if (command.equals("find")) {
                printer.customerNameInput();
                String name = bufferedReader.readLine();
                Optional<BlackCustomer> findCustomer = customerService.findByName(name);
                if(findCustomer.isEmpty()){
                    printer.printBlackCustomerIsNotPresent(name);
                    continue;
                }
                System.out.println(findCustomer);
            } else if (command.equals("exit")) {
                break;
            }
        }
    }
}
