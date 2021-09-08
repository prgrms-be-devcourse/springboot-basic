package prgms.springbasic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.springbasic.console.Printer;
import prgms.springbasic.customer.Customer;
import prgms.springbasic.customer.CustomerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CustomerBlacklistApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerBlacklistAppConfig.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Printer printer = new Printer();

        while (true) {
            printer.printCustomerCommandList();
            String command = bufferedReader.readLine();
            if (command.equals("create")) {
                printer.customerNameInput();
                String name = bufferedReader.readLine();
                Customer customer = customerService.createCustomer(name);
                System.out.println("create customer success!!");
                System.out.println(customer.toString());
            } else if (command.equals("list")) {
                List<Customer> customerList = customerService.getCustomerList();
                printer.printCustomerList(customerList);
            } else if (command.equals("find")) {
                printer.customerNameInput();
                String name = bufferedReader.readLine();
                Customer findCustomer = customerService.findByName(name);
                if (findCustomer == null) {
                    System.out.println(name + "is not blacklist");
                    break;
                }
                System.out.println(findCustomer);
            } else if (command.equals("exit")) {
                break;
            }
        }
    }
}
