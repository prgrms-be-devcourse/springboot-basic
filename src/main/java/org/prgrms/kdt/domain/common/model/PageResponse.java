package org.prgrms.kdt.domain.common.model;

public class PageResponse<T> {
    private T data;
    private int count;

    public PageResponse(T data, int count) {
        this.data = data;
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public int getCount() {
        return count;
    }
}
