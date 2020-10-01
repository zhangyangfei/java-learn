package com.zyf.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 访问：http://localhost:8080/servlet-learn/servletdemo
//@WebServlet(description = "servlet学习1", urlPatterns = { "/servletdemo" }) // url匹配放在web.xml中
public class ServletDemo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletDemo() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("....I am ServletDemo");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
