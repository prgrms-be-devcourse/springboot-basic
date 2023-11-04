package com.programmers.springbootbasic.common.dto;

import java.util.List;

public class BaseListCountResponse<T> extends BaseListResponse<T> {

    private final int count;

    public BaseListCountResponse(List<T> data, int count) {
        super(data);
        this.count = count;
    }
}
