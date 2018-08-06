package dao.base;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import bean.DatabaseProperties;

public class DatabaseHelper {
	final Logger logger = Logger.getLogger(DatabaseHelper.class);

	DatabaseProperties databaseProperties = null;

	protected void init(DatabaseProperties databaseProperties) {
		logger.debug("DatabaseHelper init metodu çalışmaya başladı.");
		this.databaseProperties = databaseProperties;
		logger.debug("DatabaseHelper init metodu çalışması bitti.");
	}

	public Connection getConnection() throws Exception {
		logger.debug("DatabaseHelper getConnection metodu çalışmaya başladı.");
		if (databaseProperties.isDataSource()) {
			logger.debug("DatabaseHelper getConnection metodu çalışması bitti.");
			return getDataSourceConnection();
		} else {
			logger.debug("DatabaseHelper getConnection metodu çalışması bitti.");
			return getTransactionalConnection();
		}
	}

	public Connection getRegularConnection() throws Exception {
		logger.debug("DatabaseHelper getRegularConnection metodu çalışmaya başladı.");
		Connection conn = null;
		try {
			Class.forName(databaseProperties.getDatabaseDriver()).newInstance();
			conn = (Connection) DriverManager.getConnection(databaseProperties.getDatabaseConnectionURL(),
					databaseProperties.getUsername(), databaseProperties.getPassword());
		} catch (Exception exception) {
			logger.fatal("DatabaseHelper getRegularConnection metodu exeption = " + exception);
			throw exception;
		}
		logger.debug("DatabaseHelper getRegularConnection metodu çalışması bitti.");
		return conn;
	}

	public Connection getDataSourceConnection() throws Exception {
		logger.debug("DatabaseHelper getDataSourceConnection metodu çalışmaya başladı.");
		Context initContext = new InitialContext();
		DataSource ds = (DataSource) initContext.lookup(databaseProperties.getJndiName());
		Connection conn = ds.getConnection();
		conn.setAutoCommit(false);
		logger.debug("DatabaseHelper getDataSourceConnection metodu çalışması bitti.");
		return conn;

	}

	public Connection getTransactionalConnection() throws Exception {
		logger.debug("DatabaseHelper getTransactionalConnection metodu çalışmaya başladı.");
		Connection conn = getRegularConnection();
		conn.setAutoCommit(false);
		logger.debug("DatabaseHelper getTransactionalConnection metodu çalışması bitti.");
		return conn;
	}

	public void closeConnection(Connection conn) throws Exception {
		logger.debug("DatabaseHelper closeConnection metodu çalışmaya başladı.");
		if (!conn.isClosed()) {
			try {
				conn.close();
			} catch (Exception exception) {
				logger.fatal("DatabaseHelper getTransactionalConnection metodu exeption = " + exception);
			}
		}
		logger.debug("DatabaseHelper closeConnection metodu çalışması bitti.");

	}

}
