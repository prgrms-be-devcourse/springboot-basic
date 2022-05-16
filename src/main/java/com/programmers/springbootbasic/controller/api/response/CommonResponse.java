package com.programmers.springbootbasic.controller.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommonResponse extends Response {

    private Object body;
    private String description;

}
