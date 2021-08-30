package org.prgrms.kdt.kdtspringorder.common.io;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;

import java.util.List;

class FileCvsIoTest {

    @Test
    void write() {
    }

    @Test
    void readAllLines() {
        FileCvsIo fileCvsIo = new FileCvsIo();
        List<Customer> customers = fileCvsIo.readAllLines();
        customers.forEach(customer -> System.out.println(customer.toString()));
    }

}
