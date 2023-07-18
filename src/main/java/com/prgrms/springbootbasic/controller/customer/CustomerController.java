package com.prgrms.springbootbasic.controller.customer;

import com.prgrms.springbootbasic.dto.customer.request.CustomerCreateRequest;
import com.prgrms.springbootbasic.dto.customer.request.CustomerUpdateRequest;
import com.prgrms.springbootbasic.dto.customer.response.CustomerListResponse;
import com.prgrms.springbootbasic.dto.customer.response.CustomerResponse;
import com.prgrms.springbootbasic.service.customer.CustomerService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //(Create)고객 생성
    public CustomerResponse create(CustomerCreateRequest customerCreateRequest) {
        return customerService.createCustomer(customerCreateRequest);
    }

    //(Read) 고객의 모든 리스트 출력
    public CustomerListResponse findAllList() {
        return customerService.findAllCustomers();
    }

    //(Read) - UUID를 통해서 검색
    public CustomerResponse findById(UUID customerId) {
        return customerService.findById(customerId);
    }


    //(Read) 고객 생성기간 출력
    public CustomerListResponse findByCreateAt() {
        return customerService.findByCreateAt();
    }


    //(Update) 고객 수정
    public void update(CustomerUpdateRequest customerUpdateRequset) {
        customerService.updateCustomer(customerUpdateRequset);
    }

    //(Delete) 특정 고객을 찾아 삭제
    public int deleteById(UUID customerId) {
        return customerService.deleteById(customerId);
    }

    //(Delete) 모든 고객 내용 삭제
    public void deleteAll() {
        customerService.deleteAllCustomer();
    }

    public boolean checkCustomerId(UUID voucherId) {
        return customerService.existById(voucherId);
    }
}