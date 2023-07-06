package org.prgrms.kdt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParam extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse
            resp) throws ServletException, IOException {
        System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("age"));
    }
    }

