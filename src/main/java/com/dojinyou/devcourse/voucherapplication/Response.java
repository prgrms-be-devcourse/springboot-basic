package com.dojinyou.devcourse.voucherapplication;

public class Response<T> {
    private final State state;
    private final T data;

    Response(State state, T data) {
        this.state = state;
        this.data = data;
    }

    @Override
    public String toString() {
        return "응답 결과("+this.state+"): "+data.toString();
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
