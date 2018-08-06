package dao.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import bean.DatabaseProperties;
import dao.base.DatabaseHelper;
import dto.CustomerDTO;
import dto.CustomerPhoneDTO;

public class CustomerDAO extends DatabaseHelper {
	final Logger logger = Logger.getLogger(CustomerDAO.class);

	public void init(Properties appProperties) {
		logger.debug("CustomerDAO init metodu çalışmaya başladı.");
		DatabaseProperties databaseProperties = new DatabaseProperties();
		databaseProperties.setUsername(appProperties.getProperty("dbuser"));
		databaseProperties.setPassword(appProperties.getProperty("dbpassword"));
		databaseProperties.setDatabaseConnectionURL(appProperties.getProperty("database"));
		databaseProperties.setDatabaseDriver(appProperties.getProperty("databaseDriver"));
		databaseProperties.setJndiName(appProperties.getProperty("jndiName"));
		databaseProperties.setDataSource(Boolean.parseBoolean(appProperties.getProperty("isDataSource")));
		super.init(databaseProperties);
		logger.debug("CustomerDAO init metodu çalışması bitti.");
	}

	public void addCustomer(CustomerDTO customerDTO) throws Exception {
		logger.debug("CustomerDAO addCustomer metodu çalışmaya başladı.");
		Connection conn = getConnection();
		try {
			addCustomerDatabase(customerDTO, conn);
			for (CustomerPhoneDTO customerPhoneDTO : customerDTO.getCustomerPhoneDTOList()) {
				customerPhoneDTO.setCustomerId(customerDTO.getId());
				addCustomerPhone(customerPhoneDTO, conn);
			}
			conn.commit();
		} catch (Exception e) {
			logger.error("CustomerDAO addCustomer metodu exeption = " + e);
			conn.rollback();
		} finally {
			closeConnection(conn);
			logger.debug("CustomerDAO addCustomer metodu çalışması bitti.");
		}
	}

	public CustomerDTO addCustomerDatabase(CustomerDTO customer, Connection conn) throws Exception {
		logger.debug("CustomerDAO addCustomer metodu çalışmaya başladı.");
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		StringBuilder query = new StringBuilder();
		query.append("insert into musteri(MAdi,MSoyadi) values(?,?)");
		try {
			preparedStatement = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getSurname());
			logger.trace(query.toString());
			preparedStatement.execute();
			rs = preparedStatement.getGeneratedKeys();
			while (rs.next()) {
				int customerId = rs.getInt(1);
				customer.setId(customerId);
			}
			return customer;
		} catch (Exception e) {
			logger.error("CustomerDAO addCustomerDatabase metodu exeption = " + e);
			throw e;
		} finally {
			preparedStatement.close();
			rs.close();
			logger.debug("CustomerDAO addCustomer metodu çalışması bitti.");
		}
	}

	public void addCustomerPhone(CustomerPhoneDTO customerPhoneDTO, Connection connection) throws Exception {
		logger.debug("CustomerDAO addCustomerPhone metodu çalışmaya başladı.");
		PreparedStatement preparedStatement = null;
		StringBuilder query = new StringBuilder();
		query.append("insert into tel (TelNumber,MID) values (?,?)");
		try {
			preparedStatement = connection.prepareStatement(query.toString());
			preparedStatement.setString(1, customerPhoneDTO.getPhoneNumber());
			preparedStatement.setLong(2, customerPhoneDTO.getCustomerId());
			preparedStatement.execute();
			logger.trace(query.toString());
		} catch (Exception e) {
			logger.error("CustomerDAO addCustomerPhone metodu exeption = " + e);
			throw e;
		} finally {
			preparedStatement.close();
			logger.debug("CustomerDAO addCustomerPhone metodu çalışması bitti.");
		}
	}

	public List<CustomerPhoneDTO> getNumbers(long id, Connection conn) throws Exception {
		logger.debug("CustomerDAO getNumbers metodu çalışmaya başladı.");
		StringBuilder query = new StringBuilder();
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		query.append("SELECT TelID,TelNumber ");
		query.append("FROM tel ");
		query.append("WHERE MID = ?");
		preparedStatement = conn.prepareStatement(query.toString());
		preparedStatement.setLong(1, id);
		preparedStatement.executeQuery();
		logger.trace(query.toString());
		rs = preparedStatement.getResultSet();
		List<CustomerPhoneDTO> customerPhoneDTOs = new ArrayList<>();
		while (rs.next()) {
			CustomerPhoneDTO customerPhoneDTO = new CustomerPhoneDTO();
			customerPhoneDTO.setId(rs.getLong(1));
			customerPhoneDTO.setPhoneNumber(rs.getString(2));
			customerPhoneDTO.setCustomerId(id);
			customerPhoneDTOs.add(customerPhoneDTO);
		}
		logger.debug("CustomerDAO getNumbers metodu çalışması bitti.");
		return customerPhoneDTOs;
	}

	public List<CustomerDTO> getCustomers() throws Exception {
		logger.debug("CustomerDAO getCustomers metodu çalışmaya başladı.");
		Connection conn = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<CustomerDTO> customerList = new ArrayList<CustomerDTO>();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT * ");
			query.append("FROM musteri");
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.executeQuery();
			rs = preparedStatement.getResultSet();
			logger.trace(query.toString());
			while (rs.next()) {
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setId(rs.getLong(1));
				customerDTO.setName(rs.getString(2));
				customerDTO.setSurname(rs.getString(3));
				customerDTO.setCustomerPhoneDTOList(getNumbers(customerDTO.getId(), conn));
				customerList.add(customerDTO);
			}
			return customerList;
		} catch (Exception e) {
			logger.error("CustomerDAO getCustomers metodu exeption = " + e);
			throw e;
		} finally {
			preparedStatement.close();
			rs.close();
			closeConnection(conn);
			logger.debug("CustomerDAO getCustomers metodu çalışması bitti.");
		}
	}

	public CustomerDTO getCustomer(long customerId) throws Exception {
		logger.debug("CustomerDAO getCustomer metodu çalışmaya başladı.");
		Connection conn = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT * ");
			query.append("FROM musteri ");
			query.append("WHERE MID = ?");

			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setLong(1, customerId);
			preparedStatement.execute();
			rs = preparedStatement.getResultSet();
			logger.trace(query.toString());
			CustomerDTO customerDTO = new CustomerDTO();
			while (rs.next()) {
				customerDTO.setId(rs.getLong(1));
				customerDTO.setName(rs.getString(2));
				customerDTO.setSurname(rs.getString(3));
				customerDTO.setCustomerPhoneDTOList(getNumbers(customerDTO.getId(), conn));
			}
			return customerDTO;
		} catch (Exception e) {
			logger.error("CustomerDAO getCustomer metodu exeption = " + e);
			throw e;
		} finally {
			preparedStatement.close();
			rs.close();
			closeConnection(conn);
			logger.debug("CustomerDAO getCustomer metodu çalışması bitti.");
		}
	}

	public int removeCustomer(Long id) throws Exception {
		logger.debug("CustomerDAO removeCustomer metodu çalışmaya başladı.");
		Connection conn = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			StringBuilder query1 = new StringBuilder();
			query1.append("DELETE ");
			query1.append("FROM tel ");
			query1.append("WHERE MID =  ?");
			preparedStatement = conn.prepareStatement(query1.toString());
			preparedStatement.setLong(1, id);

			StringBuilder query = new StringBuilder();
			preparedStatement.execute();
			preparedStatement.close();

			query.append("DELETE ");
			query.append("FROM musteri ");
			query.append("WHERE MID = ?");
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setLong(1, id);
			preparedStatement.execute();

			conn.commit();
			logger.trace(query.toString());
		} catch (Exception e) {
			logger.error("CustomerDAO removeCustomer metodu exeption = " + e);
			conn.rollback();
			throw e;
		} finally {
			preparedStatement.close();
			closeConnection(conn);
			logger.debug("CustomerDAO removeCustomer metodu çalışması bitti.");
		}
		return 0;
	}

	public void updateCustomerDatabase(CustomerDTO customerDTO , Connection conn) throws Exception {
		logger.debug("CustomerDAO updateCustomerDatabase metodu çalışmaya başladı.");
		PreparedStatement preparedStatement = null;
		StringBuilder query = new StringBuilder();
		query.append("UPDATE musteri ");
		query.append("SET MAdi=? , MSoyadi=? ");
		query.append("WHERE MID= ? ");
		try {
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setString(1, customerDTO.getName());
			preparedStatement.setString(2, customerDTO.getSurname());
			preparedStatement.setLong(3, customerDTO.getId());
			preparedStatement.execute();

			logger.trace(query.toString());
		} catch (Exception e) {
			logger.error("CustomerDAO updateCustomerDatabase metodu exeption = " + e);
			throw e;
		} finally {
			preparedStatement.close();
			logger.debug("CustomerDAO updateCustomerDatabase metodu çalışması bitti.");
		}
	}
	
	public void updateCustomerPhones (CustomerPhoneDTO customerPhoneDTO, Connection conn) throws Exception {
		logger.debug("CustomerDAO updateCustomerPhones metodu çalışmaya başladı.");
		PreparedStatement preparedStatement = null;
		StringBuilder query = new StringBuilder();
		query.append("UPDATE tel ");
		query.append("SET TelNumber=? ");
		query.append("WHERE MID= ? and TelID= ? ");
		try {
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setString(1, customerPhoneDTO.getPhoneNumber());
			preparedStatement.setLong(2, customerPhoneDTO.getCustomerId());
			preparedStatement.setLong(3, customerPhoneDTO.getId());
			preparedStatement.execute();
			logger.trace(query.toString());
		} catch (Exception e) {
			logger.error("CustomerDAO updateCustomerPhones metodu exeption = " + e);
			throw e;
		} finally {
			preparedStatement.close();
			logger.debug("CustomerDAO updateCustomerPhones metodu çalışması bitti.");
		}
	}


	public void updateCustomer(CustomerDTO customerDTO) throws Exception {
		logger.debug("CustomerDAO updateCustomer metodu çalışmaya başladı.");
		Connection conn = getConnection();
		try {
			updateCustomerDatabase(customerDTO, conn);
			for (CustomerPhoneDTO customerPhoneDTO : customerDTO.getCustomerPhoneDTOList()) {
				customerPhoneDTO.setCustomerId(customerDTO.getId());
				updateCustomerPhones(customerPhoneDTO, conn);
			}
			conn.commit();
		} catch (Exception e) {
			logger.error("CustomerDAO updateCustomer metodu exeption = " + e);
			conn.rollback();
		} finally {
			closeConnection(conn);
			logger.debug("CustomerDAO updateCustomer metodu çalışması bitti.");
		}
	}

	/*
	 * 
	 * public List<CustomerDTO> getCustomers() throws Exception {
	 * logger.debug("CustomerDAO getCustomers metodu çalışmaya başladı.");
	 * Connection conn = getConnection(); PreparedStatement preparedStatement =
	 * null; ResultSet rs = null; try { StringBuilder query = new StringBuilder();
	 * query.
	 * append("SELECT musteri.MID, musteri.MAdi, musteri.MSoyadi, tel.TelID, tel.TelNumber "
	 * ); query.append("FROM musteri "); query.append("INNER JOIN tel ");
	 * query.append("ON musteri.MID=tel.MID ORDER BY musteri.MID "); //iki query ile
	 * çaliştir. preparedStatement = conn.prepareStatement(query.toString());
	 * preparedStatement.execute(); logger.trace(query.toString()); rs =
	 * preparedStatement.getResultSet(); HashMap<Integer, CustomerDTO> hmap = new
	 * HashMap<Integer, CustomerDTO>(); while (rs.next()) { if
	 * (hmap.containsKey((int)rs.getLong(1))) { CustomerPhoneDTO phone = new
	 * CustomerPhoneDTO(); phone.setId(rs.getLong(4));
	 * phone.setPhoneNumber(rs.getString(5)); phone.setCustomerId(rs.getLong(1));
	 * hmap.get((int)rs.getLong(1)).getCustomerPhoneDTOList().add(phone); }else {
	 * CustomerDTO customer = new CustomerDTO(); customer.setId(rs.getLong(1));
	 * customer.setName(rs.getString(2)); customer.setSurname(rs.getString(3));
	 * List<CustomerPhoneDTO> customerPhones = new ArrayList<>(); CustomerPhoneDTO
	 * phone = new CustomerPhoneDTO(); phone.setId(rs.getLong(4));
	 * phone.setPhoneNumber(rs.getString(5)); phone.setCustomerId(rs.getLong(1));
	 * customerPhones.add(phone); customer.setCustomerPhoneDTOList(customerPhones);
	 * hmap.put((int) rs.getLong(1),customer); } } List<CustomerDTO> customerDTOs =
	 * new ArrayList<CustomerDTO>(hmap.values()); return customerDTOs; } catch
	 * (Exception e) {
	 * logger.error("CustomerDAO getCustomers metodu exeption = "+e); throw e; }
	 * finally { preparedStatement.close(); rs.close(); closeConnection(conn);
	 * logger.debug("CustomerDAO getCustomers metodu çalışması bitti."); } }
	 */

}
