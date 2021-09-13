package prgms.springbasic.customer;

import java.util.List;

public interface BlackCustomerService {
    BlackCustomer createCustomer(String name);

    BlackCustomer findByName(String name);

    List<BlackCustomer> getCustomerList();
}
