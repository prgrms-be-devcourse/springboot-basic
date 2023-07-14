package com.programmers.springbootbasic.service.dto.Customer;

/*
    사실 상 dto 내부는 다 똑같은데 class 이름 가진 의미가 있다고 생각해서 3개 만들었는데
    어떻게 생각하시나요?
 */
public record CustomerCreationRequest(
        String name,
        String email
) {
}
