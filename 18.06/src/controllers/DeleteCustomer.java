package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ServiceFacede;

public class DeleteCustomer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long customerId = Long.valueOf(request.getParameter("customerId"));

		
		try {
			ServiceFacede.getInstance().removeCustomer(customerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("customerList.jsp");
	}
}