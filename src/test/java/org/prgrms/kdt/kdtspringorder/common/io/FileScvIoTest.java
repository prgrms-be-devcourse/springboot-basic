package org.prgrms.kdt.kdtspringorder.common.io;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileScvIoTest {

    @Test
    void write() {
    }

    @Test
    void readAllLines() {
        FileScvIo fileScvIo = new FileScvIo();
        List<Customer> customers = fileScvIo.readAllLines();
        customers.forEach(customer -> System.out.println(customer.toString()));
    }

}
