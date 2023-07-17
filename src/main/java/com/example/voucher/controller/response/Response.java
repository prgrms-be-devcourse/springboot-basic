package com.example.voucher.controller.response;

import java.lang.reflect.Field;
import java.util.List;

public class Response<T> {
    private List<T> dtoList;

    public Response(List<T> dtoList) {
        this.dtoList = dtoList;
    }

    public String getResultMessage() {
        StringBuilder resultMessage = new StringBuilder();

        for (T dto : dtoList) {
            Field[] fields = dto.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // 필드에 접근할 수 있도록 설정

                try {
                    Object value = field.get(dto);
                    resultMessage.append(field.getName()).append(": ").append(value).append(", ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            resultMessage.deleteCharAt(resultMessage.length() - 2);
            resultMessage.append("\n");
        }

        return resultMessage.toString();
    }

}
