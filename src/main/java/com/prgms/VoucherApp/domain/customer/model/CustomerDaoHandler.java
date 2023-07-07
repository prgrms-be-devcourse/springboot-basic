package com.prgms.VoucherApp.domain.customer.model;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateReqDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomerResDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateReqDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerDaoHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDaoHandler.class);
    private final BlackListFileDao blackListStorage;
    private final CustomerDao customerDao;

    public CustomerDaoHandler(BlackListFileDao blackListStorage, CustomerDao customerDao) {
        this.blackListStorage = blackListStorage;
        this.customerDao = customerDao;
    }

    @Transactional
    public void save(CustomerCreateReqDto requestDto) {
        Customer customer = new Customer(UUID.randomUUID(), requestDto.getCustomerStatus());
        customerDao.save(customer);
        logger.info("저장된 고객의 정보 id : {}, status : {}", customer.getCustomerId(), customer.getCustomerStatus());
    }

    public CustomersResDto findAll() {
        List<Customer> findCustomers = customerDao.findAll();

        List<CustomerResDto> convertCustomerResDto = findCustomers.stream()
            .map(CustomerResDto::new)
            .toList();

        return new CustomersResDto(convertCustomerResDto);
    }

    public Optional<CustomerResDto> findOne(UUID id) {
        return customerDao.findById(id)
            .map(CustomerResDto::new);
    }

    public CustomersResDto findByStatus(CustomerCreateReqDto requestDto) {
        List<Customer> findCustomers = customerDao.findByCustomerStatus(requestDto.getCustomerStatus());

        List<CustomerResDto> convertCustomerResDto = findCustomers.stream()
            .map(CustomerResDto::new)
            .toList();

        return new CustomersResDto(convertCustomerResDto);
    }

    @Transactional
    public void update(CustomerUpdateReqDto requestDto) {
        customerDao.updateStatus(requestDto);
    }

    @Transactional
    public void deleteById(UUID id) {
        customerDao.deleteById(id);
    }

    public CustomersResDto readBlackLists() {
        List<Customer> findBlacklists = blackListStorage.findBlacklist();

        List<CustomerResDto> convertCustomerResDto = findBlacklists.stream()
            .map(CustomerResDto::new)
            .toList();

        return new CustomersResDto(convertCustomerResDto);
    }
}
