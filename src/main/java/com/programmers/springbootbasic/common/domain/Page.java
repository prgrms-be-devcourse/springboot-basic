package com.programmers.springbootbasic.common.domain;

import java.util.List;
import java.util.function.Function;

public class Page<T> {
    private final List<T> contents;
    private final int numberOfElements;
    private final Pageable pageable;
    private final int totalPage;
    private final long totalElements;

    public Page(List<T> contents, Pageable pageable, long totalElements) {
        this.contents = contents;
        this.numberOfElements = contents.size();
        this.pageable = pageable;
        this.totalElements = totalElements;
        this.totalPage = calculateTotalPage();
    }

    private int calculateTotalPage() {
        return (int) ((totalElements / pageable.size()) + (totalElements % pageable.size() == 0 ? 0 : 1));
    }

    public <U> Page<U> map(Function<T, U> converter) {
        List<U> converted = this.contents.stream()
                .map(converter)
                .toList();
        return new Page<>(converted, this.pageable, this.totalElements);
    }

    public List<T> getContents() {
        return contents;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public boolean isLast() {
        return pageable.page() + 1 >= totalPage;
    }
}
