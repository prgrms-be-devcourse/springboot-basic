package org.prgrms.springbasic.service.console.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbasic.domain.console.Console;
import org.prgrms.springbasic.repository.customer.BlackCustomerRepository;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.service.console.ConsoleService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListBlackServiceTest {

    CustomerRepository repository = new BlackCustomerRepository();
    ConsoleService service = new ListBlackService(repository, new Console());

    @Test
    @DisplayName("블랙회원이 존재하지 않는 경우 예외가 발생해야한다.")
    void testValidateCustomers() throws Exception {
        var validateCustomers = service.getClass().getDeclaredMethod("validateCustomers", List.class);
        validateCustomers.setAccessible(true);

        assertThrows(InvocationTargetException.class, () -> validateCustomers.invoke(service, repository.findAll()));
    }
}