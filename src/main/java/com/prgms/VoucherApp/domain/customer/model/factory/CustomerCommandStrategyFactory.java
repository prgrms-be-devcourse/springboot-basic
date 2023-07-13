package com.prgms.VoucherApp.domain.customer.model.factory;

import com.prgms.VoucherApp.domain.customer.model.strategy.*;
import com.prgms.VoucherApp.view.CustomerCommand;


public final class CustomerCommandStrategyFactory {

    private CustomerCommandStrategyFactory() {
    }

    public static CustomerCommandStrategy from(CustomerCommand customerCommand) {

        CustomerCommandStrategy customerCommandStrategy = switch (customerCommand) {
            case CREATE -> new CustomerCreate();
            case FIND_ALL -> new CustomerFindAll();
            case FIND_ONE -> new CustomerFindOne();
            case FIND_BY_STATUS -> new CustomerFindByStatus();
            case FIND_BLACKLIST -> new CustomerFindBlacklist();
            case UPDATE -> new CustomerUpdate();
            case DELETE -> new CustomerDelete();
            case EXIT -> new CustomerExit();
        };

        return customerCommandStrategy;
    }
}
