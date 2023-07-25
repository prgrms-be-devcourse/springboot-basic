package com.wonu606.vouchermanager.repository.customer.reader;

import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import java.util.List;

public interface CustomerReader {

    List<CustomerResultSet> findAll();
}
