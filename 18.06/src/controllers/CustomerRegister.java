package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import dto.CustomerDTO;
import dto.CustomerPhoneDTO;

public class CustomerRegister extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final Logger logger = Logger.getLogger(CustomerRegister.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		logger.debug("CustomerRegister doPost metodu çalışmaya başladı.");
		String adi = req.getParameter("name");
		String soyadi = req.getParameter("surname");
		String tel = req.getParameter("phoneNumber1");
		String tel1 = req.getParameter("phoneNumber2");

		CustomerDTO newCustomer = new CustomerDTO();
		newCustomer.setName(adi);
		newCustomer.setSurname(soyadi);

		CustomerPhoneDTO newPhone = new CustomerPhoneDTO();
		newPhone.setPhoneNumber(tel);

		CustomerPhoneDTO newPhone1 = new CustomerPhoneDTO();
		newPhone1.setPhoneNumber(tel1);

		List<CustomerPhoneDTO> customerPhoneDTOList = new ArrayList<>();
		customerPhoneDTOList.add(newPhone);
		customerPhoneDTOList.add(newPhone1);

		newCustomer.setCustomerPhoneDTOList(customerPhoneDTOList);
		boolean kayitlimi = false;
		try {
		//	kayitlimi = ServiceFacede.getInstance().addCustomer(newCustomer);
		} catch (Exception e) {
			logger.error("CustomerRegister doPost metodu exeption = " + e);
		}
		
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();

		if (kayitlimi) {
			out.println("Kaydiniz Yapilmistir.");
		} else {
			out.println("Kaydiniz Yapilmamistir.");
		}
		logger.debug("CustomerRegister doPost metodu çalışması bitti.");

	}
}
