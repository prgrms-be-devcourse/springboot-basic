package prgms.springbasic.customer;

import java.util.List;

public interface BlackCustomerRepository {
    BlackCustomer save(BlackCustomer customer);

    BlackCustomer findByName(String name);

    List<BlackCustomer> getCustomerList();
}
