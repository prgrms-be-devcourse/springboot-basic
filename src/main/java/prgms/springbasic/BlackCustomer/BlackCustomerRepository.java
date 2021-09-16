package prgms.springbasic.BlackCustomer;

import java.util.List;
import java.util.Optional;

public interface BlackCustomerRepository {
    BlackCustomer save(BlackCustomer customer);

    Optional<BlackCustomer> findByName(String name);

    List<BlackCustomer> getCustomerList();
}
