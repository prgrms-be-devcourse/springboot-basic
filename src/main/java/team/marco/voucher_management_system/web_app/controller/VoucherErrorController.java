package team.marco.voucher_management_system.web_app.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoucherErrorController implements ErrorController {
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = Integer.parseInt(status.toString());

        response.setStatus(statusCode);

        List<String> header = getHeader(request);

        model.addAttribute("status", status);
        model.addAttribute("message", HttpStatus.valueOf(statusCode).getReasonPhrase());
        model.addAttribute("header", header);

        return "error/error";
    }

    private List<String> getHeader(HttpServletRequest request) {
        List<String> headerItems = new ArrayList<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            headerItems.add(MessageFormat.format("{0} : {1}", headerName, request.getHeader(headerName)));
        }

        return headerItems;
    }
}
