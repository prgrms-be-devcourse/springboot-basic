package com.programmers.springweekly.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    // ControllerAdvice로 처리되지 않는 서블릿 수준의 예외와 404 에러 처리
    @GetMapping("/error")
    public String errorThrow(HttpServletRequest httpServletRequest, Model model) {
        Object errorStatusCode = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = httpServletRequest.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute("errorCode", errorStatusCode);

        if ((Integer) errorStatusCode == 404) {
            model.addAttribute("errorMsg", "요청하신 페이지를 찾을 수 없습니다.");
            return "errorPage";
        }

        model.addAttribute("errorMsg", errorMessage);

        return "errorPage";
    }

}
