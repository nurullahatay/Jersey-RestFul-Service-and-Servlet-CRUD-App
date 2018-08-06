package controllers;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		session.invalidate();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("User "+ request.getRemoteUser() +" has been logged out.");
		
		
	}
}