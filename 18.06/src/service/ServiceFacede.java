package service;

import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import dao.customer.CustomerDAO;
import dto.CustomerDTO;

public class ServiceFacede {

	private static ServiceFacede serviceFacede;
	private CustomerDAO customerDAO;
	final Logger logger = Logger.getLogger(ServiceFacede.class);

	private ServiceFacede() {
	}

	public static ServiceFacede getInstance() {
		if (serviceFacede == null) {
			serviceFacede = new ServiceFacede();
		}
		return serviceFacede;
	}

	public void initialize(Properties appProperties) throws Exception {
		logger.info("ServiceFacede initialize metodu çalışmaya başladı.");
		customerDAO = new CustomerDAO();
		customerDAO.init(appProperties);
		logger.info("ServiceFacede initialize metodunun çalışması bitti.");

	}

	public void shutdown() {

	}

	public void addCustomer(CustomerDTO newCustomer) throws Exception {
		customerDAO.addCustomer(newCustomer);
	}

	public List<CustomerDTO> getCustomers() throws Exception {
		return customerDAO.getCustomers();
	}
	
	public int removeCustomer(Long id) throws Exception {
		return customerDAO.removeCustomer(id);
	}
	
	public CustomerDTO getCustomer(long id) throws Exception {
		return customerDAO.getCustomer(id);
	}
	
	public void updateCustomer (CustomerDTO customerDTO) throws Exception{
		customerDAO.updateCustomer(customerDTO);
	}
}
