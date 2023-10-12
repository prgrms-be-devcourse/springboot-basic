package com.programmers.springbootbasic.common.response.model;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResult<T> extends CommonResult {
    private final List<T> data;

    public ListResult(List<T> data) {
        super(true, "완료");
        this.data = data;
    }
}
