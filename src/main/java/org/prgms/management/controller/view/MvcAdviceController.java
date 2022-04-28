package org.prgms.management.controller.view;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice("org.prgms.management.controller.view")
public class MvcAdviceController {
    // TODO : 에러 핸들러 상황에 따라 세분화 시키기 ( 에러 클래스 생성 -> 핸들러로 관리 )
    @ExceptionHandler(RuntimeException.class)
    public String runtimeException(Exception e, Model model) {
        model.addAttribute("errMsg", e.getMessage());
        return "error/runtime";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFoundException() {
        return "error/404";
    }
}
