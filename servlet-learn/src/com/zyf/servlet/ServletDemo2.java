package com.zyf.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 访问：http://localhost:8080/servlet-learn/servletdemo2
@WebServlet(description = "servlet学习2", urlPatterns = { "/servletdemo2" })
public class ServletDemo2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletDemo2() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(" I am ServlerDemo2");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
