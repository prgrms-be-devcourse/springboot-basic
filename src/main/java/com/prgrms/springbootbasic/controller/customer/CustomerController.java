package com.prgrms.springbootbasic.controller.customer;

import com.prgrms.springbootbasic.dto.customer.request.CustomerCreateRequest;
import com.prgrms.springbootbasic.dto.customer.request.CustomerUpdateRequest;
import com.prgrms.springbootbasic.dto.customer.response.CustomerListResponse;
import com.prgrms.springbootbasic.dto.customer.response.CustomerResponse;
import com.prgrms.springbootbasic.service.customer.CustomerService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    //(Create)고객 생성
    public void create(CustomerCreateRequest customerCreateRequest) {
        customerService.createCustomer(customerCreateRequest);
    }


    //(Read) 고객의 모든 리스트 출력
    public CustomerListResponse findAllList() {
        return customerService.findAllCustomers();
    }

    //(Read) 고객 생성기간 출력
    public CustomerResponse findByCreateAt(LocalDateTime createAt) {
        return customerService.findByCreateAt(createAt);
    }

    //(Update) 고객 수정
    public void update(CustomerUpdateRequest customerUpdateRequset) {
        customerService.updateCustomer(customerUpdateRequset);
    }

    //(Delete) 특정 고객을 찾아 삭제
    public void deleteById(UUID customerId) {
        customerService.deleteById(customerId);
    }

    //(Delete) 모든 고객 내용 삭제
    public void deleteAll() {
        customerService.deleteAllCustomer();
    }
}
