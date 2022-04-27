package org.prgms.io;

import org.prgms.domain.Customer;

import java.io.File;
import java.util.List;

public interface FileReader {
    List<Customer> readFile(File file) throws Exception;

    List<Customer> readFile() throws Exception;
}
