package org.prgrms.kdt.kdtspringorder.custommer.service;

import org.prgrms.kdt.kdtspringorder.common.io.FileIo;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListCustomerService {

    private final FileIo<Customer> fileIo;

    public BlackListCustomerService(@Qualifier("file-scv-io") FileIo<Customer> fileIo) {
        this.fileIo = fileIo;
    }

    public List<Customer> getBlackList() {
        return this.fileIo.readAllLines();
    }

}
