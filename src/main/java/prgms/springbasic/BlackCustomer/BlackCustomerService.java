package prgms.springbasic.BlackCustomer;

import java.util.List;
import java.util.Optional;

public interface BlackCustomerService {
    BlackCustomer saveCustomer(String name);

    Optional<BlackCustomer> findByName(String name);

    List<BlackCustomer> getCustomerList();

    void listIsEmpty();
}
