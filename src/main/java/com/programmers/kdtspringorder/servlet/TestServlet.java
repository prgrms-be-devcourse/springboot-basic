package com.programmers.kdtspringorder.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(value = "/*", loadOnStartup = 1)
public class TestServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestURI = req.getRequestURI();
        logger.info("Got Request from {} ", requestURI);

        resp.getWriter().println("Hello Servlet");
    }
}
