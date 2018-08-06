package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dto.CustomerDTO;
import service.ServiceFacede;

public class ShowCustomers extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final Logger logger = Logger.getLogger(ShowCustomers.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("ShowCustomers doGettodu çalışmaya başladı.");
		List<CustomerDTO> customerList = new ArrayList<>();

		try {
			List<CustomerDTO> customerDTOs = ServiceFacede.getInstance().getCustomers();
			customerList = customerDTOs;
		} catch (Exception e) {
			logger.error("ShowCustomers doGet metodu exeption = " + e);
		}

		
		req.setAttribute("customerList", customerList);

		RequestDispatcher dispatcher = req.getRequestDispatcher("customers.jsp");
		dispatcher.forward(req, resp);
		logger.debug("ShowCustomers doGettodu çalışması bitti.");
	}

}
