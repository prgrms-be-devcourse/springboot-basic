package com.programmers.customer.dto;

import java.util.UUID;

//4차 PR
public record CustomerRequestDto (UUID customerId, String name){
}
