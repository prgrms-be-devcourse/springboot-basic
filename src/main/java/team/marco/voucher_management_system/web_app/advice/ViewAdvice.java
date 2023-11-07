package team.marco.voucher_management_system.web_app.advice;

import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import team.marco.voucher_management_system.web_app.controller.CustomerController;

@ControllerAdvice(basePackageClasses = CustomerController.class)
public class ViewAdvice {
    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementHandler() {
        return "error/404";
    }
}
