package org.prgms.management.controller.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("org.prgms.management.controller.api")
public class ApiAdviceController {
    // TODO : 에러 핸들러 상황에 따라 세분화 시키기 ( 에러 클래스 생성 -> 핸들러로 관리 )
    @ExceptionHandler(RuntimeException.class)
    public String runtimeException(Exception e) {
        return e.getMessage();
    }
}