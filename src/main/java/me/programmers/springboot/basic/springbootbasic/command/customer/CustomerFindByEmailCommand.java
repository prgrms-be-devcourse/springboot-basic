package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;

public class CustomerFindByEmailCommand implements CommandStrategy {
    public CustomerFindByEmailCommand(CustomerService customerService) {
    }

    @Override
    public void operateCommand() {
        
    }
}
