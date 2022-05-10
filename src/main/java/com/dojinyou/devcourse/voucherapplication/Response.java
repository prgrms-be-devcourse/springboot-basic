package com.dojinyou.devcourse.voucherapplication;

import java.util.List;

public class Response<T> {
    private final State state;
    private final T data;

    public Response(State state, T data) {
        this.state = state;
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("응답 결과(" + this.state + "): ");
        if (data instanceof List) {
            ((List<T>) data).stream()
                            .map(sb::append);
        } else {
            sb.append(data.toString());
        }
        return sb.toString();
    }

    public State getState() {
        return this.state;
    }

    public T getData() {
        return this.data;
    }

    public enum State {
        SUCCESS,
        FAIL
    }
}
