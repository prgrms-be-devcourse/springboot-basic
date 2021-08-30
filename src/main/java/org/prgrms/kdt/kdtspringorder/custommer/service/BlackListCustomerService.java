package org.prgrms.kdt.kdtspringorder.custommer.service;

import org.prgrms.kdt.kdtspringorder.common.io.FileIo;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.prgrms.kdt.kdtspringorder.voucher.application.VoucherCommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListCustomerService {

    private static final Logger logger = LoggerFactory.getLogger(BlackListCustomerService.class);

    private final FileIo<Customer> fileIo;

    public BlackListCustomerService(@Qualifier("file-cvs-io") FileIo<Customer> fileIo) {
        this.fileIo = fileIo;
    }

    public List<Customer> getBlackList() {
        logger.info("Access getBlackList()");
        return this.fileIo.readAllLines();
    }

}
