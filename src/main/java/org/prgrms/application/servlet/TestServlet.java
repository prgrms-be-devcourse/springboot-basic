package org.prgrms.application.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//@WebServlet(value = "/*",loadOnStartup = 1)
public class TestServlet extends HttpServlet {

    private static final Logger logger =LoggerFactory.getLogger(TestServlet .class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var requestURI = req.getRequestURI();
        logger.info("Got Request from {}", requestURI);

        var writer = resp.getWriter();
        writer.print("hello servlet");
    }
}
