package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vlad-Andrei Balcanu
 */

public class ConnectionFactory {

	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse";
	private static final String USER = "root";
	private static final String PASS = "Mitoche1";

	private static ConnectionFactory singleInstance = new ConnectionFactory();

	/**
	 * <p>
	 *     Constructorul clasei
	 * </p>
	 */
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 *     Metoda prin care creem conexiunea la baza de date
	 * </p>
	 * @return un obiect de tip Connection (conexiunea)
	 */

	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * <p>
	 *     Metoda prin care luam o instanta de tipul Connection
	 * </p>
	 * @return instanta a unui obiect de tipul Connection
	 */

	public static Connection getConnection() {
		return singleInstance.createConnection();
	}

	/**
	 * <p>
	 *     Metoda de inchidere a conexiunii
	 * </p>
	 * @param connection obiectul de tip Connection ce trebuie inchis
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
			}
		}
	}

	/**
	 * <p>
	 *     Metoda de inchidere a unei interogari
	 * </p>
	 * @param statement interogarea ce trebuie inchisa
	 */

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
			}
		}
	}

	/**
	 * <p>
	 *     Metoda de inchidere a unui set de date extrase
	 * </p>
	 * @param resultSet setul de date exstrase
	 */

	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
			}
		}
	}
}
