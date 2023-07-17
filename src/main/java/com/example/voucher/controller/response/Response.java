package com.example.voucher.controller.response;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Response<T> {

    private static final int DELETE_SIZE = 2;

    private List<T> responses;

    public Response(List<T> responses) {
        this.responses = responses;
    }

    public Response(T response) {
        this.responses = Arrays.asList(response);
    }

    public String getResultMessage() {
        StringBuilder resultMessage = new StringBuilder();

        for (T response : responses) {
            Field[] fields = response.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // 필드에 접근할 수 있도록 설정

                try {
                    Object value = field.get(response);
                    resultMessage.append(field.getName()).append(": ").append(value).append(", ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            resultMessage.deleteCharAt(resultMessage.length() - DELETE_SIZE);
            resultMessage.append("\n");
        }

        return resultMessage.toString();
    }

}
